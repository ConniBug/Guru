package com.guru.paged;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import com.guru.bot.Guru;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;

public class PagedEmbed extends ListenerAdapter{

	private int page;
	private Function<PagedEmbed.PagedData, EmbedBuilder> embeds;
	private int elementsPerPage;
	private List<String> items;
	private MessageReceivedEvent event;
	private Date start;
	private final long TIMEOUT = 1000*60*1;
	private boolean userOnly;
	
	private String backID;
	private String nextID;
	
	private Message message;

	public void setEmbeds(Function<PagedEmbed.PagedData, EmbedBuilder> embeds) {
		this.embeds = embeds;
	}

	public void setElementsPerPage(int elementsPerPage) {
		this.elementsPerPage = elementsPerPage;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public void setEvent(MessageReceivedEvent event) {
		this.event = event;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setUserOnly(boolean userOnly) {
		this.userOnly = userOnly;
	}

	public void setBackID(String backID) {
		this.backID = backID;
	}

	public void setNextID(String nextID) {
		this.nextID = nextID;
	}
	
	public class PagedData{
		private final List<String> pagedElements;
		private final int page;
		private final PagedEmbed parent;
		public PagedData(List<String> pagedElements, int page, PagedEmbed parent) {
			this.pagedElements = pagedElements;
			this.page = page;
			this.parent = parent;
		}
		public void forEach(Consumer<? super String> data) {
			this.pagedElements.forEach(data);
		}
		public List<String> getPagedElements() {
			return pagedElements;
		}
		public int getPage() {
			return page;
		}
		public PagedEmbed getParent() {
			return parent;
		}
	}
	
	/**
	 * 
	 * @param event the MessageReceivedEvent even where this embed was created
	 * @param page the current page number
	 * @param items the items to display
	 * @param userOnly whether or not the command sender should be the sole person to use interactions
	 * @param embeds function that takes in an embed provided the page data
	 * @param elementsPerPage the amount of elements visible in the page
	 */
	public PagedEmbed(MessageReceivedEvent event, int page, List<String> items, boolean userOnly, Function<PagedEmbed.PagedData, EmbedBuilder> embeds, int elementsPerPage) {
		this.page = page;
		this.embeds = embeds;
		this.items = items;
		this.elementsPerPage = elementsPerPage;
		this.event = event;
		this.userOnly = userOnly;
	}
	
	/**
	 * 
	 * @param event the MessageReceivedEvent even where this embed was created
	 * @param items the items to display
	 * @param userOnly whether or not the command sender should be the sole person to use interactions
	 * @param embeds function that takes in an embed provided the page data
	 * @param elementsPerPage the amount of elements visible in the page
	 */
	public PagedEmbed(MessageReceivedEvent event, List<String> items, boolean userOnly, Function<PagedEmbed.PagedData, EmbedBuilder> embeds, int elementsPerPage) {
		this.page = 1;
		this.embeds = embeds;
		this.items = items;
		this.elementsPerPage = elementsPerPage;
		this.event = event;
		this.userOnly = userOnly;
	}

	/**
	 * @param event the MessageReceivedEvent even where this embed was created
	 * @param items the items to display
	 * @param userOnly whether or not the command sender should be the sole person to use interactions
	 * @param builder function that takes in an embed provided the page data
	 * @param elementsPerPage the amount of elements visible in the page
	 * 
	 * @return an instance of the page embed
	 */
	public static PagedEmbed create(MessageReceivedEvent event, List<String> items, int elementsPerPage, boolean userOnly, Function<PagedEmbed.PagedData, EmbedBuilder> builder) {
		PagedEmbed embed = new PagedEmbed(event, 1, items, userOnly, builder, elementsPerPage);
		Guru.getInstance().getJDA().addEventListener(embed);
		return embed;
	}
	
	/**
	 * @return get the current page
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * @param page the new page to be set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * @return return this instance with the next page
	 */
	public PagedEmbed next() {	
		this.page = (this.page*this.elementsPerPage) < this.items.size() ? (this.page+1) : this.page;
		return this;
	}
	
	/**
	 * @return return this instance with the previous page
	 */
	public PagedEmbed prev() {
		this.page = (this.page-1) <= 1 ? 1 : this.page-1;
		return this;
	}
	
	/**
	 * 
	 * @param page the new page to be set
	 * @return return this instance with the defined page
	 */
	public PagedEmbed page(int page) {
		if(page <= 0) {
			this.page = 0;
		}
		if((this.page*this.elementsPerPage) > this.items.size()) {
			this.page = this.getLastPage();
		}
		return this;
	}
	
	public int getLastPage() {
		int lastPage = (int)(this.items.size() / this.elementsPerPage);
		if(this.items.size() >= (lastPage*this.elementsPerPage)) {
			lastPage += 1;
		}
		return lastPage;
	}
	
	/**
	 * @return an embedbuilder for the current page
	 */
	public EmbedBuilder build() {
		int min = ((page-1)*elementsPerPage) <= 0 ? 0 : ((page-1)*elementsPerPage);
		int max = ((page)*elementsPerPage) > this.items.size() ? this.items.size() : ((page)*elementsPerPage);
		
		List<String> items = this.items.subList(min, max);
		
		PagedEmbed.PagedData page = new PagedEmbed.PagedData(items, this.page, this);

		return this.embeds.apply(page);
	}
	
	/**
	 * send the embed builder to the user
	 */
	public void sendAsReply() {
		
		this.start = new Date();
		
		this.backID = UUID.randomUUID().toString();
		this.nextID = UUID.randomUUID().toString();
		
		Button back = Button.of(ButtonStyle.SECONDARY, this.backID, "Previous");
		Button front = Button.of(ButtonStyle.SECONDARY, this.nextID, "Next");
		
		List<Button> buttons = new ArrayList<>();
		buttons.add(back);
		buttons.add(front);
		
		this.setMessage(this.event.getMessage().replyEmbeds(this.build().build()).addActionRow(buttons).complete());
		
	}
	
	
	public void fromMessage(Message message) {
		
		this.start = new Date();
		
		this.backID = UUID.randomUUID().toString();
		this.nextID = UUID.randomUUID().toString();
		
		Button back = Button.of(ButtonStyle.SECONDARY, this.backID, "Previous");
		Button front = Button.of(ButtonStyle.SECONDARY, this.nextID, "Next");
		
		List<Button> buttons = new ArrayList<>();
		buttons.add(back);
		buttons.add(front);
		
		MessageEditBuilder msg = MessageEditBuilder.fromMessage(message).clear().setActionRow(buttons).setEmbeds(this.build().build());
		this.setMessage(message.editMessage(msg.build()).complete());

	}
	
	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		super.onButtonInteraction(event);
		
		if(userOnly && !event.getMember().getId().equals(this.event.getMember().getId())) {
			return;
		}
		
		long passed = System.currentTimeMillis() - this.start.getTime();
		
		if(passed >= TIMEOUT) {
			Guru.getInstance().getJDA().removeEventListener(this);
			return;
		}
		
		Button back = Button.of(ButtonStyle.SECONDARY, this.backID, "Previous");
		Button front = Button.of(ButtonStyle.SECONDARY, this.nextID, "Next");
		
		List<Button> buttons = new ArrayList<>();
		buttons.add(back);
		buttons.add(front);
		
		if(event.getButton().getId().equals(backID)) {
			
			MessageEditBuilder msg = MessageEditBuilder.fromMessage(event.getMessage()).clear().setEmbeds(this.prev().build().build());
			event.deferEdit().applyData(msg.build()).setActionRow(buttons).queue();
		}
		if(event.getButton().getId().equals(nextID)) {
			
			MessageEditBuilder msg = MessageEditBuilder.fromMessage(event.getMessage()).clear().setEmbeds(this.next().build().build());
			event.deferEdit().applyData(msg.build()).setActionRow(buttons).queue();
		}
	}

	public Function<PagedEmbed.PagedData, EmbedBuilder> getEmbeds() {
		return embeds;
	}

	public int getElementsPerPage() {
		return elementsPerPage;
	}

	public List<String> getItems() {
		return items;
	}

	public MessageReceivedEvent getEvent() {
		return event;
	}

	public Date getStart() {
		return start;
	}

	public long getTIMEOUT() {
		return TIMEOUT;
	}

	public boolean isUserOnly() {
		return userOnly;
	}

	public String getBackID() {
		return backID;
	}

	public String getNextID() {
		return nextID;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}

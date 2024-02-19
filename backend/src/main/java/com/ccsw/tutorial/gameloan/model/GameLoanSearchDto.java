package com.ccsw.tutorial.gameloan.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;
import java.time.LocalDateTime;

/**
 * @author ccsw
 *
 */
public class GameLoanSearchDto {
	private String game_title;
	private Long client_id;
	private LocalDateTime date;

    private PageableRequest pageable;
	
	public String getGame_title() {
		return game_title;
	}

	public void setGame_title(String game_title) {
		this.game_title = game_title;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}

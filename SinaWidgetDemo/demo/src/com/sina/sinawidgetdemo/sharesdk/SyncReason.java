package com.sina.sinawidgetdemo.sharesdk;

/**
 * Reasons of AccountInfoManager synchronize.
 * 
 * @author liu_chonghui
 * 
 */
public enum SyncReason {
	/**
	 * total score only
	 */
	TOTAL_SCORE,

	/**
	 * gift count only
	 */
	GIFT_STATISTICS,

	/**
	 * game count only
	 */
	GAME_STATISTICS,

	/**
	 * collect count only
	 */
	COLLECT_STATISTICS,

	/**
	 * current tasks state only
	 */
	USER_TASKS,

	/**
	 * above all
	 */
	ALL,

	/**
	 * total score & current tasks(special)
	 */
	TOTAL_SCORE_AND_USER_TASKS,
}

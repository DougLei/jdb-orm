package com.douglei.orm.mapping.handler.entity;

/**
 * 
 * @author DougLei
 */
public enum Mode {
	
	/**
	 * 添加或覆盖
	 */
	ADD_OR_COVER(2),
	
	/**
	 * 删除
	 */
	DELETE(1){
		@Override
		public int sort(MappingEntity o1, MappingEntity o2) {
			return super.sort(o1, o2) * -1;
		}
	};
	
	private int priority; // 执行的优先级, 优先级越低越优先, 在操作多个映射时, 会按照优先级的顺序操作
	private Mode(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	/**
	 * 将o1和o2比较, 进行排序; 默认进行ADD_OR_COVER时的比较
	 * @param o1
	 * @param o2
	 * @return
	 */
	public int sort(MappingEntity o1, MappingEntity o2) {
		if(o1.getType().getPriority() < o2.getType().getPriority())
			return -1;
		if(o1.getType().getPriority() > o2.getType().getPriority())
			return 1;
		
		if(o1.getOrder() < o2.getOrder())
			return -1;
		if(o1.getOrder() > o2.getOrder())
			return 1;
		return 0;
	}
}

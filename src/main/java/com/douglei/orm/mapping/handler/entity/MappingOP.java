package com.douglei.orm.mapping.handler.entity;

/**
 * mapping的操作
 * @author DougLei
 */
public enum MappingOP {
	
	/**
	 * 添加或覆盖
	 */
	ADD_OR_COVER(2){
		@Override
		public int compare4Sort(MappingEntity o1, MappingEntity o2) {
			if(o1.getType().getPriority() < o2.getType().getPriority())
				return -1;
			if(o1.getType().getPriority() > o2.getType().getPriority())
				return 1;
			return 0;
		}
	},
	
	/**
	 * 删除
	 */
	DELETE(1){
		@Override
		public int compare4Sort(MappingEntity o1, MappingEntity o2) {
			if(o1.getType().getPriority() > o2.getType().getPriority())
				return -1;
			if(o1.getType().getPriority() < o2.getType().getPriority())
				return 1;
			return 0;
		}
	},
	
	/**
	 * 只删除数据库对象, 和映射容器没有关系, 目前是针对存储过程和视图
	 */
	DELETE_DATABASE_OBJECT_ONLY(1){
		@Override
		public int compare4Sort(MappingEntity o1, MappingEntity o2) {
			if(o1.getType().getPriority() > o2.getType().getPriority())
				return -1;
			if(o1.getType().getPriority() < o2.getType().getPriority())
				return 1;
			return 0;
		}
	};
	
	private int priority; // 执行的优先级, 优先级越低越优先, 在操作多个映射时, 会按照优先级的顺序操作
	private MappingOP(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	/**
	 * 将o1和o2比较, 进行排序
	 * @param o1
	 * @param o2
	 * @return
	 */
	public abstract int compare4Sort(MappingEntity o1, MappingEntity o2);
}

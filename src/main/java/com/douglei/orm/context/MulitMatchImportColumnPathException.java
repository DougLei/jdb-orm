package com.douglei.orm.context;

import java.util.List;

/**
 * 多个匹配path [<import-columns path="">] 异常
 * @author DougLei
 */
public class MulitMatchImportColumnPathException extends RuntimeException{
	private static final long serialVersionUID = 1739259129274817063L;

	public MulitMatchImportColumnPathException(String importColumnPath, List<String> filePaths) {
		super("根据指定path=["+importColumnPath+"], 扫描到多个相同的文件, 请检查:" + filePaths.toString());
	}
}

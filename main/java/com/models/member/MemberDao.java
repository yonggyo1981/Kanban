package com.models.member;

/**
 * MemberDao 클래스 
 *
 */
public class MemberDao {
	private static MemberDao instance = new MemberDao();
	private MemberDao() {};  // 기본 생성자 private -> 외부 생성 X, 내부에서만 생성 O
	
	public static MemberDao getInstance() {
		if (instance != null) {
			instance = new MemberDao();
		}
		return instance;
	}
}

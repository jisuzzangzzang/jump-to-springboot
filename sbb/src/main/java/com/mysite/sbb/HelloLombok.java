package com.mysite.sbb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
// import lombok.Setter;

// @RequiredArgsConstructor
@Getter
// @Setter

public class HelloLombok {

	private final String hello; // final을 적용하여 @Setter 의미 X, 메소드도 사용할 수 없음
								// final = 한번 설정한 값을 변경할 수 없게 하는 키워드
	private final int lombok;

	public HelloLombok(String hello, int lombok) {
		this.hello = hello;
		this.lombok = lombok;
	}
	
	public static void main(String[] args) {
		HelloLombok helloLombok = new HelloLombok("헬로", 5);
		
		// helloLombok.setHello("헬로우");
		// helloLombok.setLombok(5);

		System.out.println(helloLombok.getHello());
		System.out.println(helloLombok.getLombok());

	}
}
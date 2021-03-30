package com.smart.controller;

public class Test {

	public static void main(String[] args) {
		System.out.println("////");
	int a[] =  {3,1,5,0};
	
	int start = 0;
	int end  = a.length-1;
	while(start<end) {
		
int m = a[start];
a[start] =  a[end];
a[end] =  m;
start++;
end--;
	}
	System.out.println("//");
	for (int i = 0; i < a.length; i++) {
		System.out.println(a[i]);
	}
		
	}
	
}

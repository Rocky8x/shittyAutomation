package com.android.Objects;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageBase {

	public Element getOkButton() {

		return new Element(By.id("btn_confirm"), "Button OK");
	}

	public Element getOkCancel() {

		return new Element(By.id(""), "Button cancel");
	}
}

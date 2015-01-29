package fr.jasmineconseil.bookangular.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

@XmlRootElement
public class Book implements Serializable {

	@Id @ObjectId 
	private String id;

	@NotNull
	private String isbn;

	@NotNull
	private String title;

	@Min(1)
	private int pages;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (isbn != null && !isbn.trim().isEmpty())
			result += "isbn: " + isbn;
		if (title != null && !title.trim().isEmpty())
			result += ", title: " + title;
		result += ", pages: " + pages;
		return result;
	}
}
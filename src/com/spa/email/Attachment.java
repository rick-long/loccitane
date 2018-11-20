package com.spa.email;

public class Attachment implements java.io.Serializable {
	private String path;
	private String url;
	private String description;
	private String name;

    private Attachment() {
    }

    public static Attachment getInstance() {
        return new Attachment();
    }
    
    public static Attachment getInstance(String path) {
        Attachment attachment = new Attachment();
        attachment.setPath(path);
        return attachment;
    }

    public String getPath() {
		return path;
	}

	public Attachment setPath(String path) {
		this.path = path;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Attachment setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Attachment setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getName() {
		return name;
	}

	public Attachment setName(String name) {
		this.name = name;
		return this;
	}
}

package br.com.amarques.smartcookbook.crawler.exeception;

public class DownloadImagemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DownloadImagemException(String message) {
        super(message);
    }
}

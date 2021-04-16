package br.com.amarques.smartcookbook.crawler.exeception;

public class AcessoPaginaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AcessoPaginaException(String message) {
        super(message);
    }
}

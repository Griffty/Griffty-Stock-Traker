package org.example;

public abstract class ProgramInterface {
    protected static ProgramInterface self;
    protected final HTTPHandler httpHandler = HTTPHandler.getInstance();
    protected final TradeHandler tradeHandler = TradeHandler.getInstance();
    protected final FileHandler fileHandler = FileHandler.getInstance();
    protected ProgramInterface(){}
}

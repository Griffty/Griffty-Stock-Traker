package org.example;

public class SensitiveInformation {
    public static final String DiscordBotToken = API_TOKENS.DiscordBotToken;
    public static final String WindowsSaveDir = "C:\\Users\\Griffty\\Desktop\\my\\work\\coding\\Java\\StockTracker\\TempSave";
    public static final String LinuxSaveDir = "/home/griffty/JavaWork/StockTracker/save";
    public static String helpInfo = """
            > :robot: **BOT HELP GUIDE** :robot:
            >\s
            > :warning: **Notice**: This bot is currently under development. It's possible to encounter errors. If you stumble upon any, kindly DM me about them. We appreciate your understanding and patience!
            >\s
            > :chart_with_upwards_trend: **Purpose**:
            > The bot's primary purpose is to be an invaluable tool for stock trading and provide insights into the workings of stock markets. Whether you're a seasoned trader or just curious, we've got you covered!
            >\s
            > :keyboard: **How to Use**:
            > - Utilize slash commands to communicate with the bot, e.g., `/hello`.
            > - Commands might have arguments marked with `{}`. Some of these arguments are mandatory, while others are optional.\s
            >   - For instance, `/get_stock {symbol}` requires you to replace `{symbol}` with an actual stock symbol like AAPL for Apple.
            > - We're constantly expanding our feature set. For the latest additions, check the news channel or use the help command frequently.
            >\s
            > :sparkles: **Supported Commands**:
            > 1. `/stock_help`: Delivers essential information about the bot and its functionalities.
            > 2. `/get_stock {symbol}`: Fetches the current value of the stock associated with the given symbol.
            > 3. `/stock_news {symbol}`: Displays recent news articles related to the specified stock symbol.
            > 4. `/current_stocks {symbol}`: Reveals the stocks you're currently subscribed to.
            > 5. `/add_stock {symbol}`: Incorporates a new stock to your subscription list.
            > 6. `/remove_stock {symbol}`: Removes a stock from your subscription list.
            > 7. `/current_stocks_price`: Shows prices of all stocks you're currently subscribed to.
            >\s
            > :notebook_with_decorative_cover: Stay informed, trade wisely, and always keep exploring!
            """;
    public static String menuDescription = "Here you can see all information about your account. Don't worry, you are only one who can see this message. Also you can perform some other actions using buttons below.";
}

package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.example.DiscordRequestHandlers.Buttons.ButtonActionHandler;
import org.example.DiscordRequestHandlers.Buttons.BuyStockMenuButton;
import org.example.DiscordRequestHandlers.Buttons.SellStockMenuButton;
import org.example.DiscordRequestHandlers.Commands.*;
import org.example.DiscordRequestHandlers.Modals.BuyStockModalAction;
import org.example.DiscordRequestHandlers.Modals.ModalActionHandler;
import org.example.DiscordRequestHandlers.Modals.SellStockModalAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ServerInterface extends ProgramInterface{
    public HashSet<SlashCommandStructure> supportedResponseCommands;

    public static ServerInterface getInstance() {
        if (self == null){
            self = new ServerInterface();
        }
        return (ServerInterface) self;
    }
    private ServerInterface(){
        JDA api = JDABuilder.createDefault(SensitiveInformation.DiscordBotToken).build();
        createCommands();
        List<SlashCommandData> commandData = new ArrayList<>();
        for (SlashCommandStructure structure : supportedResponseCommands){
            SlashCommandData data = Commands.slash(structure.getCommandId(), structure.getDescription());
            for (int i = 0; i < structure.getListOfOptions().size(); i++){
                CommandOption option = structure.getListOfOptions().get(i);
                data.addOption(option.getOptionType(), option.getOptionID(), option.getDescription(), option.isRequired(), option.isAutoComplete());
            }
            commandData.add(data);
        }
        api.updateCommands().addCommands(commandData).queue();
        api.addEventListener(CommandActionHandler.getInstance());
        api.addEventListener(ButtonActionHandler.getInstance());
        api.addEventListener(ModalActionHandler.getInstance());
    }

    private void createCommands() {
        supportedResponseCommands = new HashSet<>(){
            {
                add(new SlashCommandStructure("menu", "Open user menu.", null));

                add(new SlashCommandStructure("register", "Register yourself in a system.", null));
                add(new SlashCommandStructure("help", "Get help in using me.", null));
                add(new SlashCommandStructure("get_money", "shows current money", null));
                add(new SlashCommandStructure("get_stock", "Shows price of specified stock.", new ArrayList<>(){
                    {
                        add(new CommandOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to check.", true, false));
                    }
                }));
                add(new SlashCommandStructure("stock_news", "Show last news about specific stock.", new ArrayList<>(){
                    {
                        add(new CommandOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to check.", true, false));
                        add(new CommandOption(OptionType.INTEGER, "amount", "How many articles you want to get.", true, false));
                    }
                }));


                add(new SlashCommandStructure("subscribed_stocks", "Shows prices of all stocks you are subscribed to.", null));
                add(new SlashCommandStructure("add_stock", "Adds stock to your subscription list.", new ArrayList<>(){
                    {
                        add(new CommandOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to add.", true, false));
                    }
                }));
                add(new SlashCommandStructure("remove_stock", "Removes stock from your subscription list.", new ArrayList<>(){
                    {
                        add(new CommandOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to remove.", true, false));
                    }
                }));

                add(new SlashCommandStructure("owned_stocks", "Shows list of all stocks you have in property.", null));
                add(new SlashCommandStructure("buy_stock", "Buy some stocks.", new ArrayList<>(){
                    {
                        add(new CommandOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to buy.", true, false));
                        add(new CommandOption(OptionType.INTEGER, "amount", "How much you want to buy.", true, false));
                    }
                }));
                add(new SlashCommandStructure("sell_stock", "Sell some stocks.", new ArrayList<>(){
                    {
                        add(new CommandOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to sell.", true, false));
                        add(new CommandOption(OptionType.INTEGER, "amount", "How much you want to sell.", true, false));
                    }
                }));
            }
        };
        CommandActionHandler.getInstance().registerNewCommand(new RegisterCommandAction("register"))
                .registerNewCommand(new HelpCommandAction("help"))
                .registerNewCommand(new GetMoneyCommandAction("get_money"))
                .registerNewCommand(new GetStockCommandAction("get_stock"))
                .registerNewCommand(new GetNewsCommandAction("stock_news"))
                .registerNewCommand(new GetSubscribedStocksCommandAction("subscribed_stocks"))
                .registerNewCommand(new AddSubscribedStockCommandAction("add_stock"))
                .registerNewCommand(new RemoveSubscribedStockCommandAction("remove_stock"))
                .registerNewCommand(new GetOwnedStocksCommandAction("owned_stocks"))
                .registerNewCommand(new BuyStockCommandAction("buy_stock"))
                .registerNewCommand(new SellStockCommandAction("sell_stock"))

                .registerNewCommand(new MenuCommandAction("menu"));

        ButtonActionHandler.getInstance().registerNewButtonAction(new BuyStockMenuButton("buyStockMenuB"))
                .registerNewButtonAction(new SellStockMenuButton("sellStockMenuB"));

        ModalActionHandler.getInstance().registerNewModalAction(new BuyStockModalAction("buyStockM", List.of("symbol", "amount")))
                .registerNewModalAction(new SellStockModalAction("sellStockM", List.of("symbol", "amount")));
    }
    public List<CommandOption> getOptionsForCommand(String commandId){
        for (SlashCommandStructure structure : supportedResponseCommands){
            if (structure.getCommandId().equals(commandId)){
                return structure.getListOfOptions();
            }
        }
        return null;
    }
}

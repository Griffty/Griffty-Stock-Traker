package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.example.DiscordRequestHandlers.*;

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
        api.addEventListener(CommandHandler.getInstance());
//        api.updateCommands().addCommands(
//                Commands.slash("register", "Register yourself in a system."),
//                Commands.slash("stock_help", "Get help in using me."),
//
//                Commands.slash("subscribed_stocks", "Shows prices of all stocks you are subscribed to."),
//                Commands.slash("add_stock", "Adds stock to your subscription list.")
//                        .addOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to add.", true),
//                Commands.slash("remove_stock", "Removes stock from your subscription list.")
//                        .addOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to remove.", true, true),
//
//                Commands.slash("owned_stocks", "Shows list of all stocks you have in property."),
//                Commands.slash("buy_stock", "Buy some stocks.")
//                        .addOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to buy.", true)
//                        .addOption(OptionType.INTEGER, "amount", "How much stock you want to buy.", true),
//                Commands.slash("sell_stock", "Sell some stocks.")
//                        .addOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to sell.", true, true)
//                        .addOption(OptionType.INTEGER, "amount", "How much stock you want to sell.", true, true),
//
//                Commands.slash("get_stock", "Shows price of specified stock.")
//                        .addOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to check.", true),
//                Commands.slash("stock_news", "Show last news about specific stock.")
//                        .addOption(OptionType.STRING, "symbol", "Symbol of a stock that you want to check.", true)
//                        .addOption(OptionType.STRING, "amount", "How many articles you want to get.", true),
//                Commands.slash("admin", "admin commands").
//                        addOption(OptionType.STRING, "parameters", "admin parameters", true),
//
//                Commands.slash("get_money", "Show current money.")
//                ).queue();
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
        CommandHandler.getInstance().registerNewCommand(new RegisterCommand("register"))
                .registerNewCommand(new HelpCommand("help"))
                .registerNewCommand(new GetMoneyCommand("get_money"))
                .registerNewCommand(new GetStockCommand("get_stock"))
                .registerNewCommand(new GetNewsCommand("stock_news"))
                .registerNewCommand(new GetSubscribedStocksCommand("subscribed_stocks"))
                .registerNewCommand(new AddSubscribedStockCommand("add_stock"))
                .registerNewCommand(new RemoveSubscribedStockCommand("remove_stock"))
                .registerNewCommand(new GetOwnedStocksCommand("owned_stocks"))
                .registerNewCommand(new BuyStockCommand("buy_stock"))
                .registerNewCommand(new SellStockCommand("sell_stock"))

                .registerNewCommand(new MenuCommand("menu"));
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

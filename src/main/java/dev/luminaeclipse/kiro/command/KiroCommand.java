package dev.luminaeclipse.kiro.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.luminaeclipse.kiro.currency.CurrencyConverter;
import dev.luminaeclipse.kiro.init.KiroComponents;
import io.wispforest.owo.ops.TextOps;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class KiroCommand {
    public KiroCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("kiro")
                .then(literal("balance").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                        .then(argument("player", EntityArgumentType.players())
                                .then(literal("get").executes(KiroCommand::get))
                                .then(longSubcommand("set", "value", KiroCommand::set))
                                .then(longSubcommand("add", "amount", KiroCommand.modify(1)))
                                .then(longSubcommand("subtract", "amount", KiroCommand.modify(-1))))));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> longSubcommand(String name, String argName, Command<ServerCommandSource> command) {
        return literal(name).then(argument(argName, LongArgumentType.longArg(0)).executes(command));
    }

    private static int set(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        final var value = LongArgumentType.getLong(context, "value");
        final var players = EntityArgumentType.getPlayers(context, "player");

        for (var player : players) {
            KiroComponents.CURRENCY.get(player).setValue(value);
            context.getSource().sendFeedback(() -> TextOps.withColor("kiro §> balance of " + player.getEntityName() + " set to: " + value, 0xFFFFFF, TextOps.color(Formatting.GRAY)), false);
        }

        return CurrencyConverter.asInt(value);
    }

    private static int get(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        final var players = EntityArgumentType.getPlayers(context, "player");
        long totalBalance = 0;

        for (var player : players) {
            final long balance = KiroComponents.CURRENCY.get(player).getValue();
            totalBalance += balance;

            context.getSource().sendFeedback(() -> TextOps.withColor("kiro §> balance of " + player.getEntityName() + ": " + balance, 0xFFFFFF, TextOps.color(Formatting.GRAY)), false);
        }

        return CurrencyConverter.asInt(totalBalance);
    }

    private static Command<ServerCommandSource> modify(long multiplier) {
        return context -> {
            final var amount = LongArgumentType.getLong(context, "amount");
            final var players = EntityArgumentType.getPlayers(context, "player");

            long lastValue = 0;

            for (var player : players) {
                final var currencyComponent = KiroComponents.CURRENCY.get(player);

                currencyComponent.silentModify(amount * multiplier);
                lastValue = currencyComponent.getValue();

                context.getSource().sendFeedback(() -> TextOps.withColor("kiro §> balance of " + player.getEntityName() + " set to: " + currencyComponent.getValue(), 0xFFFFFF, TextOps.color(Formatting.GRAY)), false);
            }

            return CurrencyConverter.asInt(lastValue);
        };
    }

}
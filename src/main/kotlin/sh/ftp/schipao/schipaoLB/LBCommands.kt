package sh.ftp.schipao.schipaoLB

import com.mojang.brigadier.Command
import io.papermc.paper.command.brigadier.Commands

fun worldProtectorCmd(protector: WorldProtector, name: String = "wp") =
    Commands.literal(name)
        .then(Commands.literal("save").executes {
            protector.apply()
            Command.SINGLE_SUCCESS
        })
        .then(Commands.literal("reset").executes {
            protector.restore()
            Command.SINGLE_SUCCESS
        }).build()
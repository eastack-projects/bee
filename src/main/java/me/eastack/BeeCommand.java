package me.eastack;

import lombok.extern.slf4j.Slf4j;
import me.eastack.constant.Version;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "bee",
        subcommands = {SchemaSubCommand.class},
        version = Version.VERSION,
        description = "OpenAPI 代码自动生成工具",
        mixinStandardHelpOptions = true)
public class BeeCommand {

}

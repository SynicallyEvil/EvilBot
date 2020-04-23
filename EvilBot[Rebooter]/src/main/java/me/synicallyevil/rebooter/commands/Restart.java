package me.synicallyevil.rebooter.commands;

import me.synicallyevil.evilbot.EvilBot;
import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import net.dv8tion.jda.api.entities.Activity;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.List;

public class Restart implements Command {

    @Override
    public String getCommand() {
        return "restart";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("reboot");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean state) {

    }

    @Override
    public void onCommand(String[] args, CommandHandler handler) {
        if(!handler.getUser().getId().equals("338881519198404609"))
            return;

        handler.sendMessage("Attempting a reboot...");
        //handler.getEvilBot().getJDA().getPresence().setActivity(Activity.playing("Rebooting..."));

        File file = new File(System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "rebooted.txt");

        try{
            file.createNewFile();

            StringBuilder cmd = new StringBuilder();
            cmd.append(System.getProperty("java.home")).append(File.separator).append("bin").append(File.separator).append("java ");
            for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
                cmd.append(jvmArg).append(" ");
            }
            cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
            cmd.append(EvilBot.class.getName()).append(" ");
            for (String arg : args) {
                cmd.append(arg).append(" ");
            }

            Thread.currentThread().sleep(5000);
            Runtime.getRuntime().exec(cmd.toString());
            System.exit(0);
        }catch (Exception ex){
            if(file.exists())
                file.delete();
        }
    }
}

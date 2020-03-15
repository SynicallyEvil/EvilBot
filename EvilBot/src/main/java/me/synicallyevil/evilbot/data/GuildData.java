package me.synicallyevil.evilbot.data;

import net.dv8tion.jda.api.entities.Guild;

public class GuildData {

    private Guild guild;
    private char prefix;

    private String joinAndLeaveMessageChannel;
    private boolean hasJoinMessageEnabled;
    private boolean hasLeaveMessageEnabled;
    private String joinMessage;
    private String leftMessage;

    private boolean logging;
    private String loggingChannel;

    public GuildData(Guild guild){
        this.guild = guild;
        prefix = '!';

        joinAndLeaveMessageChannel = "general";
        hasJoinMessageEnabled = true;
        hasLeaveMessageEnabled = true;
        joinMessage = "Welcome member!";
        leftMessage = "Member has left!";

        logging = true;
        loggingChannel = "logs";
    }

    /**
     * Get's the Guild instance.
     * @return
     *        Guild instance.
     */
    public Guild getGuild(){
        return guild;
    }

    /**
     * Get's the command prefix.
     * @return
     *        Command prefix.
     */
    public char getPrefix(){
        return prefix;
    }

    /**
     * Set's the prefix.
     * @param letter
     *        The letter.
     */
    public void setPrefix(char letter){
        prefix = letter;
    }

    /**
     * Get's the join and leave text Channel.
     * @return
     *        The Join and Leave text Channel.
     */
    public String getJoinAndLeaveMessageChannel() {
        return joinAndLeaveMessageChannel;
    }

    /**
     * Set's the join and leave text Channel.
     * @param joinAndLeaveMessageChannel
     *        Join and Leave text Channel.
     */
    public void setJoinAndLeaveMessageChannel(String joinAndLeaveMessageChannel) {
        this.joinAndLeaveMessageChannel = joinAndLeaveMessageChannel;
    }

    /**
     * Get's the join message boolean.
     * @return
     *        The state of the join message state.
     */
    public boolean isJoinMessageEnabled() {
        return hasJoinMessageEnabled;
    }

    /**
     * Enables or disables the join message.
     * @param hasJoinMessageEnabled
     *        Enables or disables the join message.
     */
    public void setJoinMessageEnabled(boolean hasJoinMessageEnabled) {
        this.hasJoinMessageEnabled = hasJoinMessageEnabled;
    }

    /**
     * Get's the leave message boolean.
     * @return
     *        The state of the join message state.
     */
    public boolean isLeaveMessageEnabled() {
        return hasLeaveMessageEnabled;
    }

    /**
     * Enables or disables the leave message.
     * @param hasLeaveMessageEnabled
     *        Enables or disables the leave message.
     */
    public void setLeaveMessageEnabled(boolean hasLeaveMessageEnabled) {
        this.hasLeaveMessageEnabled = hasLeaveMessageEnabled;
    }

    /**
     * Get's the join message.
     * @return
     *        Join message.
     */
    public String getJoinMessage() {
        return joinMessage;
    }

    /**
     * Set's the user join message.
     * @param joinMessage
     *        The user join message.
     */
    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    /**
     * Get's the left message.
     * @return
     *        Left message.
     */
    public String getLeftMessage() {
        return leftMessage;
    }

    /**
     * Set's the user left message.
     * @param leftMessage
     *        The user left message.
     */
    public void setLeftMessage(String leftMessage) {
        this.leftMessage = leftMessage;
    }

    public String getLoggingChannel(){
        return loggingChannel;
    }

    public void setLoggingChannel(String channel){
        loggingChannel = channel;
    }

    public boolean isLogging(){
        return logging;
    }

    public void setLogging(boolean state){
        this.logging = state;
    }

}

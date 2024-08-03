package fr.vanibels.ascaquest.Quest;

import fr.vanibels.ascaquest.Ascaquest;


public class Quest {
    public Ascaquest ins = Ascaquest.instance;
    public QuestState state;
    public String Title;
    public String Description;
    public int Color;
    public Quest(String title, String lore, int color, QuestState state){
        this.Title = title;
        this.Description = lore;
        this.Color = color;
        this.state = state;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getColor() {
        return Color;
    }

    public QuestState getState() {
        return state;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setColor(int color) {
        Color = color;
    }

    public void setState(QuestState state) {
        this.state = state;
        ins.setState(state);

    }
}

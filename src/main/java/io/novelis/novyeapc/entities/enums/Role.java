package io.novelis.novyeapc.entities.enums;


public enum Role {
    COLLABORATOR("Collaborator"),
    MANAGER("Manager"),
    COMMERCIAL("Commercial"),
    HR("Hr"),
    ADMIN("Admin");

    private final String label;
    Role(String label){
        this.label = label;
    }
    public String getLabel(){
        return this.label;
    }

    @Override
    public String toString() {
        return name();
    }
}

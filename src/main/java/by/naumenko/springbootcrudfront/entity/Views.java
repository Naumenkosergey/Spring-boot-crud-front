package by.naumenko.springbootcrudfront.entity;

public final class Views {
    public interface Id {
    }

    public interface IdFirstName extends Id {
    }
    public interface IdLastName extends IdFirstName {}
    public interface Age extends IdLastName {}
    public interface Password extends Age {
    }
    public interface Role extends Age {
    }
    public interface FullUser extends Password {
    }

}

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<IFastFood> elementosMenu;

    public Menu() {
        this.elementosMenu = new ArrayList<>();
    }

    public List<IFastFood> getElementosMenu() {
        return elementosMenu;
    }

    public void setElementosMenu(List<IFastFood> elementosMenu) {
        this.elementosMenu = elementosMenu;
    }
}
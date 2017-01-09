package org.drombler.fx.core.action;

import java.text.Collator;
import javafx.scene.control.MenuItem;
import org.drombler.acp.core.action.MenuItemSortingStrategy;
import org.drombler.acp.core.action.TextMenuItemSortingStrategy;

/**
 * A JavaFX specific extension of {@link TextMenuItemSortingStrategy}. This {@link MenuItemSortingStrategy} sorts the menu items depending on their text using a locale-sensitive comparison. This
 * sorting strategy does not add any separators.
 *
 * @author puce
 */
public class FXTextMenuItemSortingStrategy extends TextMenuItemSortingStrategy<MenuItem> {

    /**
     * Creates a new instance of this class.
     */
    public FXTextMenuItemSortingStrategy() {
        super(MenuItem::getText);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param collator the {@link Collator} to use for the locale-sensitive text comparison
     */
    public FXTextMenuItemSortingStrategy(Collator collator) {
        super(MenuItem::getText, collator);
    }

}

package com.worksmobile.android.botproject.feature.chat.chatroom;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 5. 7..
 */

public class DropdownMenuLab {

    public final static int DROPDOWN_CHATROOM = 0;

    private static DropdownMenuLab dropdownMenuLab;

    private List<DropDownMenu> dropDownMenus = new ArrayList<>();

    public static DropdownMenuLab get(int type) {
        if(dropdownMenuLab == null){
            dropdownMenuLab = new DropdownMenuLab(type);
        }
        return dropdownMenuLab;
    }

    private DropdownMenuLab(int type) {
        if(type == DROPDOWN_CHATROOM){
            dropDownMenus.add(new DropDownMenu(R.string.action_invite, R.drawable.ic_action_invite));
            dropDownMenus.add(new DropDownMenu(R.string.action_setnoti, R.drawable.ic_action_setnoti));
            dropDownMenus.add(new DropDownMenu(R.string.action_exit, R.drawable.ic_action_exit));
        }

    }

    public List<DropDownMenu> getDropDownMenus() {
        return dropDownMenus;
    }

}

package com.worksmobile.android.botproject.feature.chat.chatroom;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 5. 7..
 */

public class DropdownMenuDataModel {

    public final static int DROPDOWN_CHATROOM = 0;

    private static DropdownMenuDataModel dropdownMenuDataModel;

    private List<DropDownMenu> dropDownMenus = new ArrayList<>();

    public static DropdownMenuDataModel get(int type) {
        if(dropdownMenuDataModel == null){
            dropdownMenuDataModel = new DropdownMenuDataModel(type);
        }
        return dropdownMenuDataModel;
    }

    private DropdownMenuDataModel(int type) {
        if(type == DROPDOWN_CHATROOM){
            dropDownMenus.add(new DropDownMenu(R.string.action_invite, R.drawable.ic_icon_add_user));
            dropDownMenus.add(new DropDownMenu(R.string.action_setnoti, R.drawable.ic_icon_notification));
            dropDownMenus.add(new DropDownMenu(R.string.action_exit, R.drawable.ic_icon_exit));
        }

    }

    public List<DropDownMenu> getDropDownMenus() {
        return dropDownMenus;
    }

}

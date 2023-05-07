package com.web.dev.controller;

import com.web.dev.entity.Item;
import com.web.dev.entity.SelectionHistory;
import com.web.dev.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
public class EquipmentController {

    private final ItemsService itemsService;

    @Autowired
    public EquipmentController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @RequestMapping("/cart")
    public String equipmentPage(Model model,
                                @RequestParam(required = false, name = "sport") String sport,
                                @RequestParam(required = false, name = "skill") String skill,
                                @RequestParam(required = false, name = "gender") String gender,
                                @RequestParam(required = false, name = "weight") String weight,
                                @RequestParam(required = false, name = "height") String height,
                                @AuthenticationPrincipal OidcUser user)
    {
        try {
            if (user != null) {
                model.addAttribute("fullname", user.getFullName());
            } else {
                model.addAttribute("fullname", null);
            }
            Item mainItem = null;
            Item boots = null;
            Item mount = null;
            Item poles = null;
            String poleLen = null;
            if (sport != null && skill != null && gender != null) {
                mainItem = itemsService.findMainItem(sport, skill, gender);
                boots = itemsService.findBoots(sport, skill, gender);
                if (!mainItem.getHasMount()) {
                    mount = itemsService.findMounts(sport, skill, gender);
                }
                if (sport.equals("лыжи")) {
                    poles = itemsService.findPoles();
                    poleLen = itemsService.poleLen(height);
                }
            }
            String len = null;
            if (height != null) {
                len = itemsService.lenRecommendation(sport, skill, height);
            }
            model.addAttribute("mainItem", mainItem);
            model.addAttribute("boots", boots);
            model.addAttribute("mount", mount);
            model.addAttribute("poles", poles);
            model.addAttribute("len", len);
            model.addAttribute("poleLen", poleLen);

            if (sport != null && skill != null && gender != null && weight != null && height != null && user != null) {
                SelectionHistory historyItem = new SelectionHistory();
                historyItem.setItem1(mainItem);
                historyItem.setItem2(boots);
                historyItem.setItem3(mount);
                historyItem.setItem4(poles);
                historyItem.setSport(sport);
                historyItem.setSkill(skill);
                historyItem.setGender(gender);
                historyItem.setWeight(weight);
                historyItem.setHeight(height);
                historyItem.setRecommendations(null);
                historyItem.setDateSelected(new Timestamp(System.currentTimeMillis()));
                itemsService.saveSelectionHistory(user.getSubject(), historyItem);
            }
            return "selection_equipment";
        } catch (Exception e) {
            return "error";
        }
    }
}

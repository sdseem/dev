package com.web.dev.controller;

import com.web.dev.entity.Item;
import com.web.dev.entity.SelectionHistory;
import com.web.dev.service.ItemsService;
import com.web.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Controller
public class EquipmentController {

    private final ItemsService itemsService;
    private final UserService userService;

    @Autowired
    public EquipmentController(ItemsService itemsService, UserService userService) {
        this.itemsService = itemsService;
        this.userService = userService;
    }

    @RequestMapping("/cart")
    public String equipmentPage(Model model,
                                @RequestParam(required = false, name = "sport") String sport,
                                @RequestParam(required = false, name = "skill") String skill,
                                @RequestParam(required = false, name = "gender") String gender,
                                @RequestParam(required = false, name = "weight") String weight,
                                @RequestParam(required = false, name = "height") String height,
                                @RequestParam(required = false, name = "foot") String foot,
                                @RequestParam(required = false, name = "footsize") String footSize,
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
            model.addAttribute("foot", foot);

            String mountSize = null;
            if (foot != null) {
                int t = Integer.parseInt(foot.substring(0, 2));
                if (t < 34) {
                    mountSize = "XS";
                } else if (t <= 37) {
                    mountSize = "S";
                } else if (t <= 40) {
                    mountSize = "M";
                } else {
                    mountSize = "L";
                }
            }

            List<String> recommendations = new ArrayList<>();
            if (mainItem != null) {
                if (Objects.equals(sport, "сноуборд")) {
                    recommendations.add("Рекомендуемая длина сноуборда для ваших параметров - " + len + " см");
                } else {
                    recommendations.add("Рекомендуемая длина лыж для ваших параметров - " + len + " см");
                }
            }
            if (boots != null) {
                recommendations.add("Рекомендуемая длина ботинка - " + footSize + " см");
            }
            if (mount != null) {
                recommendations.add("Рекомендуемый размер крепления - " + mountSize);
            }
            if (poles != null) {
                recommendations.add("Рекомендуемая длина горнолыжных палок - " + poleLen + " см. " +
                        "Также обратите внимание на их толщину. Если вы часто опираетесь на палки или " +
                        "у вас достаточно большой вес, то необходимо выбирать минимум 18 мм");
            }
            model.addAttribute("rec", recommendations);
            model.addAttribute("mountSize", mountSize);

            StringBuilder sb = new StringBuilder();
            for (String s : recommendations) {
                sb.append(s);
            }
            String recStr = sb.toString();

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
                historyItem.setBootSize(foot);
                historyItem.setFootSize(footSize);
                historyItem.setLen(len);
                historyItem.setPoleLen(poleLen);
                historyItem.setMountSize(mountSize);
                historyItem.setRecommendations(recStr);
                itemsService.saveSelectionHistory(user.getSubject(), historyItem);
            }
            return "selection_equipment";
        } catch (Exception e) {
            return "error";
        }
    }
}

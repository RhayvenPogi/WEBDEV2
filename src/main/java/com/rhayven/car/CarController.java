package com.rhayven.car;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, HttpSession session, Model model) {
//        KapehanUser user = (KapehanUser) session.getAttribute("user");
//        if (user == null) return "redirect:/login";

        model.addAttribute("carList", carService.searchCar(search));
        model.addAttribute("activeMenu", "home");
        return "index";
    }

    @GetMapping("/add")
    public String addCarForm(Model model, HttpSession session) {
//        KapehanUser user = (KapehanUser) session.getAttribute("user");
//        if (user == null) return "redirect:/login";

        model.addAttribute("car", new Car());
        model.addAttribute("activeMenu", "add");
        return "new";
    }

    @PostMapping("/save")
    public String saveCar(@Valid @ModelAttribute("car") Car car, BindingResult result, HttpSession session, Model model) {
//        KapehanUser user = (KapehanUser) session.getAttribute("user");
//        if (user == null) return "redirect:/login";

        if (result.hasErrors()) {
            model.addAttribute("activeMenu", "add");
            return "new";
        }

        carService.addCar(car);  // ID assigned inside service
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCar(@RequestParam("id") int id, Model model, HttpSession session) {
//        KapehanUser user = (KapehanUser) session.getAttribute("user");
//        if (user == null) return "redirect:/login";

        Car car = carService.getCar(id);
        if (car == null) return "redirect:/";

        model.addAttribute("car", car);
        model.addAttribute("activeMenu", "edit");
        return "edit";
    }

    @PostMapping("/update")
    public String updateCar(@Valid @ModelAttribute("car") Car car, BindingResult result, HttpSession session, Model model) {
//        KapehanUser user = (KapehanUser) session.getAttribute("user");
//        if (user == null) return "redirect:/login";

        if (result.hasErrors()) {
            model.addAttribute("activeMenu", "edit");
            return "edit";
        }

        carService.updateCar(car.getCarId(), car);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteCar(@RequestParam("id") int id, HttpSession session) {
//        KapehanUser user = (KapehanUser) session.getAttribute("user");
//        if (user == null) return "redirect:/login";

        carService.deleteCar(id);
        return "redirect:/";
    }

    @GetMapping("/car/{id}")
    public String viewCar(@PathVariable int id, Model model, HttpSession session) {
//        KapehanUser user = (KapehanUser) session.getAttribute("user");
//        if (user == null) return "redirect:/login";

        Car car = carService.getCar(id);
        if (car == null) return "redirect:/";

        model.addAttribute("car", car);
        model.addAttribute("activeMenu", "car");
        return "pages/car";
    }
}

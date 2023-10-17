package com.example.eco.controller;

import com.example.eco.entity.Pollution;
import com.example.eco.repo.EnterpriseRepo;
import com.example.eco.repo.PollutantRepo;
import com.example.eco.repo.PollutionRepo;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pollution")
public class PollutionController {
    private final PollutionRepo pollutionRepo;
    private final PollutantRepo pollutantRepo;
    private final EnterpriseRepo enterpriseRepo;
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("view/pollution/index");
        modelAndView.addObject("pollutions", pollutionRepo.findAll());
        return modelAndView;
    }
    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute("pollution") Pollution pollution){
        ModelAndView modelAndView =  new ModelAndView("view/pollution/add");
        modelAndView.addObject("enterprises", enterpriseRepo.findAll());
        modelAndView.addObject("pollutants", pollutantRepo.findAll());
        return modelAndView;
    }
    @PostMapping("/add")
    public ModelAndView addPost(@ModelAttribute("pollution") Pollution pollution) {
        pollutionRepo.save(pollution);
        return new ModelAndView("redirect:/pollution/index");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id")Long id){
        ModelAndView modelAndView =  new ModelAndView("view/pollution/add");
        modelAndView.addObject("pollution", pollutionRepo.findById(id).get());
        modelAndView.addObject("enterprises", enterpriseRepo.findAll());
        modelAndView.addObject("pollutants", pollutantRepo.findAll());
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        pollutionRepo.deleteById(id);
        return new ModelAndView("redirect:/pollution/index");
    }
    @PostMapping("/download")
    public ModelAndView download(@RequestParam("file") MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Pollution pollution = new Pollution();
            String s = removeCommaAndDigits(row.getCell(0).toString());
            pollution.setEnterprise(enterpriseRepo.findById(Long.valueOf(removeCommaAndDigits(row.getCell(0).toString()))).get());
            pollution.setPollutant(pollutantRepo.findById(Long.valueOf(removeCommaAndDigits(row.getCell(1).toString()))).get());
            pollution.setQuantity(Double.valueOf(row.getCell(2).toString()));
            pollution.setYear(removeCommaAndDigits(row.getCell(3).toString()));
            pollutionRepo.save(pollution);
        }
        workbook.close();
        return new ModelAndView("redirect:/pollution/index");
    }
    private String removeCommaAndDigits(String input) {
        int commaIndex = input.indexOf(".");
        if (commaIndex != -1) {
            return input.substring(0, commaIndex);
        } else {
            return input;
        }
    }
}
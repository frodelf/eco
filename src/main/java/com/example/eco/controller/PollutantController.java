package com.example.eco.controller;

import com.example.eco.entity.Pollutant;
import com.example.eco.repo.PollutantRepo;
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
@RequestMapping("/pollutant")
public class PollutantController {
    public final PollutantRepo pollutantRepo;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("view/pollutant/index");
        modelAndView.addObject("pollutants", pollutantRepo.findAll());
        return modelAndView;
    }
    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute("pollutant") Pollutant pollutant) {
        return new ModelAndView("view/pollutant/add");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id")long id) {
        ModelAndView modelAndView = new ModelAndView("view/pollutant/add");
        modelAndView.addObject("pollutant", pollutantRepo.findById(id).get());
        return modelAndView;
    }
    @PostMapping("/add")
    public ModelAndView addPost(@ModelAttribute("pollutant") Pollutant pollutant) {
        pollutantRepo.save(pollutant);
        return new ModelAndView("redirect:/pollutant/index");
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id")long id) {
        pollutantRepo.deleteById(id);
        return new ModelAndView("redirect:/pollutant/index");
    }
    @PostMapping("/download")
    public ModelAndView download(@RequestParam("file") MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        long index = 0;
        for (Row row : sheet) {
            Pollutant pollutant = new Pollutant();
            pollutant.setId(++index);
            pollutant.setName(row.getCell(0).toString());
            pollutant.setMpc(Double.valueOf(row.getCell(1).toString()));
            pollutant.setDangerous(Integer.valueOf(removeCommaAndDigits(row.getCell(2).toString())));
            pollutant.setUnitOfMeasurement(row.getCell(3).toString());
            pollutantRepo.save(pollutant);
        }
        workbook.close();
        return new ModelAndView("redirect:/pollutant/index");
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

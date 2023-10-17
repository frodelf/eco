package com.example.eco.controller;

import com.example.eco.entity.Enterprise;
import com.example.eco.repo.EnterpriseRepo;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enterprise")
public class EnterpriseController {
    private final EnterpriseRepo enterpriseRepo;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("view/enterprise/index");
        modelAndView.addObject("enterprises", enterpriseRepo.findAll());
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute("enterprise") Enterprise enterprise) {
        return new ModelAndView("view/enterprise/add");
    }

    @PostMapping("/add")
    public ModelAndView addPost(@ModelAttribute("enterprise") Enterprise enterprise) {
        enterpriseRepo.save(enterprise);
        return new ModelAndView("redirect:/enterprise/index");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("view/enterprise/add");
        modelAndView.addObject("enterprise", enterpriseRepo.findById(id).get());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        enterpriseRepo.deleteById(id);
        return new ModelAndView("redirect:/enterprise/index");
    }

    @PostMapping("/download")
    public ModelAndView download(@RequestParam("file") MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Enterprise enterprise = new Enterprise();
            enterprise.setName(row.getCell(0).toString());
            enterprise.setInfo(row.getCell(1).toString());
            enterprise.setAddress(row.getCell(2).toString());
            enterpriseRepo.save(enterprise);
        }
        workbook.close();
        return new ModelAndView("redirect:/enterprise/index");
    }
}

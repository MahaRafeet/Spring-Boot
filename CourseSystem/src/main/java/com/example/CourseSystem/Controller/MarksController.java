package com.example.CourseSystem.Controller;

import com.example.CourseSystem.RequestDTO.MarksRequestDTO;
import com.example.CourseSystem.ResponseDTO.MarksResponseDTO;
import com.example.CourseSystem.Services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/marks")
public class MarksController {

    @Autowired
    private MarksService marksService;

    // CREATE
    @PostMapping("/create")
    public MarksResponseDTO createMarks(@RequestBody MarksRequestDTO requestDTO) throws Exception {
        return marksService.createMarks(requestDTO);
    }

    // GET ALL
    @GetMapping("/getAll")
    public List<MarksResponseDTO> getAllMarks() {
        return marksService.getAllMarks();
    }

    // GET BY ID
    @GetMapping("/getById")
    public MarksResponseDTO getMarksById(@RequestParam Integer id) throws Exception {
        return marksService.getMarksById(id);
    }

    // UPDATE
    @PutMapping("/update")
    public MarksResponseDTO updateMarks(@RequestParam Integer id,
                                        @RequestBody MarksRequestDTO requestDTO) throws Exception {
        return marksService.update(id, requestDTO);
    }

    // DELETE
    @DeleteMapping("/delete")
    public String deleteMarks(@RequestParam Integer id) throws Exception {
        marksService.deleteMarks(id);   // adjust name if your service uses a different method
        return "Marks deleted successfully";
    }
}

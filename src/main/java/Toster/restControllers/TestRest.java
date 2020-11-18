package Toster.restControllers;

import Toster.entity.Test;
import Toster.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;

@Component
@RestController
@RequestMapping
public class TestRest {
    @Autowired
    private TestService testService;

    @PostMapping(value = "/test/add")
    public Test addTest(Test test) {
        return testService.addTest(test);
    }

    @DeleteMapping(value = "/test/{id}")
    public void deleteTest(@PathParam("id") Long id) {
        testService.deleteTest(id);
    }

    @GetMapping(value = "/test/{id}")
    public Test getTest(@PathParam("id") Long id) {
        return testService.getTest(id);
    }
}

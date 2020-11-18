package Toster.services;

import Toster.entity.Test;
import Toster.repositoryes.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestServiceImpl implements TestService {
    @Autowired
    private TestRepository repository;

    @Override
    public Test addTest(Test test) {
        repository.save(test);
        return test;
    }

    @Override
    public void deleteTest(Long id) {
        repository.delete(getTest(id));
    }

    @Override
    public Test getTest(Long id) {
        Optional<Test> test = repository.findById(id);
        return test.get();
    }
}

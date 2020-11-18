package Toster.services;

import Toster.entity.Test;

public interface TestService {
    Test addTest(Test test);

    void deleteTest(Long id);

    Test getTest(Long id);
}

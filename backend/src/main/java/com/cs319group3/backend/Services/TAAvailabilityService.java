package com.cs319group3.backend.Services;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.UserEntities.TA;

public interface TAAvailabilityService {
    public boolean isTAAvailable(TA ta, ClassProctoring otherCtr);
}

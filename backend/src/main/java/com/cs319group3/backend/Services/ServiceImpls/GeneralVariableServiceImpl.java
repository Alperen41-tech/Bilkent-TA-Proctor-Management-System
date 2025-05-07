package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.GeneralVariableDTO;
import com.cs319group3.backend.Entities.GeneralVariable;
import com.cs319group3.backend.Entities.Semester;
import com.cs319group3.backend.Repositories.GeneralVariableRepo;
import com.cs319group3.backend.Services.GeneralVariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class GeneralVariableServiceImpl implements GeneralVariableService {
    @Autowired
    private GeneralVariableRepo generalVariableRepo;

    @Override
    public boolean changeSemester(String semester) {
        Optional<GeneralVariable> generalVariables = generalVariableRepo.findByGeneralVariableId(1);
        GeneralVariable generalVariable;
        generalVariable = generalVariables.orElseGet(GeneralVariable::new);
        Semester sm = new Semester();
        String[] parts = semester.split(" ");
        sm.setYear(parts[0]);
        if (parts[1].equalsIgnoreCase("fall"))
            sm.setTerm(1);
        else if (parts[1].equalsIgnoreCase("spring"))
            sm.setTerm(2);
        else {
            throw new RuntimeException("Wrong term input");
        }
        generalVariable.setSemester(sm);
        generalVariableRepo.save(generalVariable);
        return true;
    }

    @Override
    public boolean changeProctoringCap(int proctoringCap) {
        Optional<GeneralVariable> generalVariables = generalVariableRepo.findByGeneralVariableId(1);
        GeneralVariable generalVariable;
        generalVariable = generalVariables.orElseGet(GeneralVariable::new);
        generalVariable.setTaProctoringCapTime(proctoringCap);
        generalVariableRepo.save(generalVariable);
        return true;
    }

    @Override
    public GeneralVariableDTO getGeneralVariable() {
        Optional<GeneralVariable> generalVariables = generalVariableRepo.findByGeneralVariableId(1);
        GeneralVariable generalVariable;
        generalVariable = generalVariables.orElseGet(GeneralVariable::new);
        GeneralVariableDTO generalVariableDTO = new GeneralVariableDTO();
        String semester = generalVariable.getSemester().getYear();
        if (generalVariable.getSemester().getTerm() == 1)
            semester = semester + " Fall";
        else if (generalVariable.getSemester().getTerm() == 2)
            semester = semester + " Spring";
        generalVariableDTO.setSemester(semester);
        generalVariableDTO.setProctoringCap(generalVariable.getTaProctoringCapTime());
        return generalVariableDTO;
    }
}

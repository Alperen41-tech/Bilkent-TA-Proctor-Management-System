package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.GeneralVariableDTO;
import com.cs319group3.backend.Entities.GeneralVariable;
import com.cs319group3.backend.Entities.Semester;
import com.cs319group3.backend.Repositories.GeneralVariableRepo;
import com.cs319group3.backend.Services.GeneralVariableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralVariableServiceImpl implements GeneralVariableService {

    private GeneralVariableRepo generalVariableRepo;

    @Override
    public boolean changeSemester(String semester) {
        List<GeneralVariable> generalVariables = generalVariableRepo.findAll();
        if (generalVariables.isEmpty())
            generalVariables.add(new GeneralVariable());
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
        return true;
    }

    @Override
    public boolean changeProctoringCap(int proctoringCap) {
        List<GeneralVariable> generalVariables = generalVariableRepo.findAll();
        if (generalVariables.isEmpty())
            generalVariables.add(new GeneralVariable());
        generalVariables.get(0).setTAProctoringCapTime(proctoringCap);
        generalVariableRepo.save(generalVariables.get(0));
        return true;
    }

    @Override
    public GeneralVariableDTO getGeneralVariable() {
        List<GeneralVariable> generalVariables = generalVariableRepo.findAll();
        GeneralVariableDTO generalVariableDTO = new GeneralVariableDTO();
        String semester = generalVariables.get(0).getSemester().getYear();
        if (generalVariables.get(0).getSemester().getTerm() == 1)
            semester = semester + " Fall";
        else if (generalVariables.get(0).getSemester().getTerm() == 2)
            semester = semester + " Spring";
        generalVariableDTO.setSemester(semester);
        generalVariableDTO.setProctoringCap(generalVariables.get(0).getTAProctoringCapTime());
        return generalVariableDTO;
    }
}

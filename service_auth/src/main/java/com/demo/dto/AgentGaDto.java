package com.demo.dto;

import java.util.List;

import com.demo.model.EtatAgentGa;
import com.demo.model.GradeAgentGa;
import com.demo.model.ServiceGa;
import lombok.Data;

@Data

public class AgentGaDto {
	private Integer idAgentGa;
	private String cinAgentGa;
	private String nomPrenomAgentGa;
	private String emailAgentGa;
	private String matAgentGa;
	private String telAgentGa;
	private String telFixAgentGa;
	private EtatAgentGa etatAgentGa;
	private Integer typeAgentGa;
	private GradeAgentGa gradeAgentGa;
	private Integer idserviceGa;
	private Boolean isResponsabel;

	private ServiceGa serviceGa;


	
	
	
}

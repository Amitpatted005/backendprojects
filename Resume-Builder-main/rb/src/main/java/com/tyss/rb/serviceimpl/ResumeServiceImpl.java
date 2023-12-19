package com.tyss.rb.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.rb.dto.AchievementDetailsDto;
import com.tyss.rb.dto.EducationDetailsDto;
import com.tyss.rb.dto.ProfileDto;
import com.tyss.rb.dto.ProjectDetailsDto;
import com.tyss.rb.dto.SkillsDto;
import com.tyss.rb.dto.SummaryDto;
import com.tyss.rb.dto.UserDto;
import com.tyss.rb.entity.AchievementDetails;
import com.tyss.rb.entity.EducationDetails;
import com.tyss.rb.entity.Profile;
import com.tyss.rb.entity.ProjectDetails;
import com.tyss.rb.entity.Skills;
import com.tyss.rb.entity.Summary;
import com.tyss.rb.entity.User;
import com.tyss.rb.exception.ResumeDetailsNotFound;
import com.tyss.rb.exception.SomethingWentWrongException;
import com.tyss.rb.repository.ProfileRepository;
import com.tyss.rb.repository.UserRepository;
import com.tyss.rb.service.ResumeService;

@Service
public class ResumeServiceImpl implements ResumeService {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Object addAllDetails(ProfileDto profileDto) {

		Profile profile = new Profile();
		try {
		// Education
		List<EducationDetails> educationDetailsList = new ArrayList<>();
		profileDto.getEducationDetailsListDto().forEach(educationDetailsDto -> {
			EducationDetails educationDetails = new EducationDetails();
			BeanUtils.copyProperties(educationDetailsDto, educationDetails);
			educationDetailsList.add(educationDetails);
		});
		profile.setEducationDetailsList(educationDetailsList);

		// ProjectDetails
		List<ProjectDetails> projectDetailsList = new ArrayList<>();
		profileDto.getProjectDetailsDto().forEach(projectDetailsDto -> {
			ProjectDetails projectDetails = new ProjectDetails();
			BeanUtils.copyProperties(projectDetailsDto, projectDetails);
			projectDetailsList.add(projectDetails);
		});
		profile.setProjectDetails(projectDetailsList);

		// skills
		Skills skills = new Skills();
		BeanUtils.copyProperties(profileDto.getSkillsDto(), skills);
		profile.setSkills(skills);

		// Achievement
		AchievementDetails achievementDetails = new AchievementDetails();
		BeanUtils.copyProperties(profileDto.getAchievementDetailsDto(), achievementDetails);
		profile.setAchievementDetails(achievementDetails);

		// Summary
		List<Summary> summaries = new ArrayList<>();
		profileDto.getSummaryDtos().forEach(summaryDto -> {
			Summary summary = new Summary();
			BeanUtils.copyProperties(summaryDto, summary);
			summaries.add(summary);
		});
		profile.setSummary(summaries);

		profile.setFirstName(profileDto.getFirstName());
		profile.setLastName(profileDto.getLastName());
		profile.setTotalExperience(profileDto.getTotalExperience());
		profile.setRelevantExperience(profileDto.getRelevantExperience());
		profile.setTechnology(profileDto.getTechnology());

		profileRepository.save(profile);

		return profileDto;
		}catch(Exception ex) {
			throw new SomethingWentWrongException("something went wrong");
		}
	}

	@Override
	public ProfileDto getAllDetails(Integer id) {
		ProfileDto profileDto = new ProfileDto();
		Profile profile = profileRepository.findById(id)
				.orElseThrow(() -> new ResumeDetailsNotFound("resume details not found"));

		// Education
		List<EducationDetailsDto> educationDetailsListDto = new ArrayList<>();
		profile.getEducationDetailsList().forEach(educationDetails -> {
			EducationDetailsDto educationDetailsDto = new EducationDetailsDto();
			BeanUtils.copyProperties(educationDetails, educationDetailsDto);
			educationDetailsListDto.add(educationDetailsDto);
		});
		profileDto.setEducationDetailsListDto(educationDetailsListDto);

		// ProjectDetails
		List<ProjectDetailsDto> projectDetailsListDto = new ArrayList<>();
		profile.getProjectDetails().forEach(projectDetails -> {
			ProjectDetailsDto projectDetailsDto = new ProjectDetailsDto();
			BeanUtils.copyProperties(projectDetails, projectDetailsDto);
			projectDetailsListDto.add(projectDetailsDto);
		});
		profileDto.setProjectDetailsDto(projectDetailsListDto);

		// skills
		SkillsDto skillsDto = new SkillsDto();
		BeanUtils.copyProperties(profile.getSkills(), skillsDto);
		profileDto.setSkillsDto(skillsDto);

		// Achievement
		AchievementDetailsDto achievementDetailsDto = new AchievementDetailsDto();
		BeanUtils.copyProperties(profile.getAchievementDetails(), achievementDetailsDto);
		profileDto.setAchievementDetailsDto(achievementDetailsDto);

		// Summary
		List<SummaryDto> summaryDtoList = new ArrayList<>();
		profile.getSummary().forEach(summary -> {
			SummaryDto summaryDto = new SummaryDto();
			BeanUtils.copyProperties(summary, summaryDto);
			summaryDtoList.add(summaryDto);
		});
		profileDto.setSummaryDtos(summaryDtoList);

		profileDto.setFirstName(profile.getFirstName());
		profileDto.setLastName(profile.getLastName());
		profileDto.setTotalExperience(profile.getTotalExperience());
		profileDto.setRelevantExperience(profile.getRelevantExperience());
		profileDto.setTechnology(profile.getTechnology());

		return profileDto;
	}

	@Override
	public UserDto addUser(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		userRepository.save(user);

		return userDto;
	}

}

package org.hackaton.kotikota.endpoint.rest.validator;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.exception.BadRequestException;
import org.hackaton.kotikota.endpoint.rest.model.CreateProject;
import org.hackaton.kotikota.endpoint.rest.model.Project;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectValidator implements Consumer<CreateProject> {
  private final ImageValidator imageValidator;
  public void accept(List<CreateProject> projects) {
    projects.forEach(this);
  }

  @Override
  public void accept(CreateProject project) {
    StringBuilder exceptionMessageBuilder = new StringBuilder();
    if (Objects.isNull(project.getId())) {
      exceptionMessageBuilder.append("Id is mandatory.");
    }
    if (Objects.isNull(project.getName())) {
      exceptionMessageBuilder.append("Name is mandatory.");
    }
    if (Objects.isNull(project.getDeadline())) {
      exceptionMessageBuilder.append("Deadline is mandatory.");
    }
    if(project.getLogo() != null) {
      exceptionMessageBuilder.append(imageValidator.getImageType(project.getLogo(), exceptionMessageBuilder));
    }
    String exceptionMessage = exceptionMessageBuilder.toString();
    if (!exceptionMessage.isBlank()) {
      throw new BadRequestException(exceptionMessage);
    }
  }
}

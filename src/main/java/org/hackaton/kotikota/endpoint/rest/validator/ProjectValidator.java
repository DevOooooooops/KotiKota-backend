package org.hackaton.kotikota.endpoint.rest.validator;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.hackaton.kotikota.endpoint.rest.exception.BadRequestException;
import org.hackaton.kotikota.endpoint.rest.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectValidator implements Consumer<Project> {
  public void accept(List<Project> projects) {
    projects.forEach(this::accept);
  }

  @Override
  public void accept(Project project) {
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
    String exceptionMessage = exceptionMessageBuilder.toString();
    if (!exceptionMessage.isBlank()) {
      throw new BadRequestException(exceptionMessage);
    }
  }
}


package acme.features.manager.userstories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userstories.UserStory;
import acme.features.manager.ManagerRepository;
import acme.roles.Manager;

@Service
public class ManagerUserStoryShowService extends AbstractService<Manager, UserStory> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerRepository			managerRepository;

	@Autowired
	private ManagerUserStoryRepository	managerUserStoryRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int managerId;
		Manager manager;

		managerId = super.getRequest().getData("id", int.class);
		manager = this.managerRepository.findOneById(managerId);

		status = super.getRequest().getPrincipal().hasRole(manager);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.managerUserStoryRepository.findOneById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "manager");

		super.getResponse().addData(dataset);
	}
}
package com.allan88.journeymanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allan88.journeymanager.data.model.ProjectResponse
import com.allan88.journeymanager.repository.ProjectRepository
import com.allan88.journeymanager.ui.common.UiState
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val repository: ProjectRepository
) : ViewModel() {

    private val _projectsState =
        MutableLiveData<UiState<List<ProjectResponse>>>(UiState.Idle)

    val projectsState: LiveData<UiState<List<ProjectResponse>>> =
        _projectsState

    fun fetchProjects() {
        viewModelScope.launch {
            _projectsState.value = UiState.Loading
            try {
                val projects = repository.getProjects() ?: emptyList()
                _projectsState.value = UiState.Success(projects)
            } catch (e: Exception) {
                _projectsState.value =
                    UiState.Error(e.message ?: "Failed to load projects")
            }
        }
    }

    fun createProject(name: String) {
        viewModelScope.launch {
            _projectsState.value = UiState.Loading
            try {
                repository.createProject(name)
                fetchProjects()
            } catch (e: Exception) {
                _projectsState.value =
                    UiState.Error(e.message ?: "Failed to create project")
            }
        }
    }
}
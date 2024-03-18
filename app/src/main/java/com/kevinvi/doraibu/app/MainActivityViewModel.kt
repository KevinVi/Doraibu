package com.kevinvi.doraibu.app

import androidx.lifecycle.ViewModel
import com.kevinvi.scan.data.repository.ScanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MainActivityViewModel @Inject constructor(val scanRepository: ScanRepository) : ViewModel() {


}
package fr.droidfactory.xrcooking.ui.presentation.mealdetails.model3d

import androidx.xr.runtime.math.Pose
import androidx.xr.runtime.math.Quaternion
import androidx.xr.runtime.math.Vector3
import androidx.xr.scenecore.GltfModel
import androidx.xr.scenecore.GltfModelEntity
import androidx.xr.scenecore.Session
import androidx.xr.scenecore.SpatialCapabilities
import kotlinx.coroutines.guava.await


internal object Wolf3D {
    private const val resourcePath = "models/wolf.gltf"
    private var wolfModel: GltfModel? = null
    private var wolfEntity: GltfModelEntity? = null

    suspend fun init(session: Session?) {
        if(session?.getSpatialCapabilities()?.hasCapability(SpatialCapabilities.SPATIAL_CAPABILITY_3D_CONTENT) == true) {
            wolfModel = session.createGltfResourceAsync(resourcePath).await()
            wolfModel?.let {
                wolfEntity = session.createGltfEntity(model = it).apply {
                    setScale(0.1f)
                    setPose(Pose(translation = Vector3(x = -0.2f, y = -0.5f), rotation = Quaternion(y = 0.9f)))
                    startAnimation(loop = true, animationName = "Eating")
                }
            }
        }
    }

    fun freeResources() {
        wolfEntity?.dispose()
        wolfEntity = null
        wolfModel = null
    }
}
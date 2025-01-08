package fr.droidfactory.xrcooking.ui.presentation.mealdetails.model3d

import androidx.xr.runtime.math.Pose
import androidx.xr.scenecore.GltfModel
import androidx.xr.scenecore.GltfModelEntity
import androidx.xr.scenecore.Session
import androidx.xr.scenecore.SpatialCapabilities
import kotlinx.coroutines.guava.await


internal object Wolf3D {
    private const val resourcePath = "models/wolf.gltf"
    private var wolfModel: GltfModel? = null
    private var wolfEntity: GltfModelEntity? = null

    private suspend fun init(session: Session?) {
        if(session?.getSpatialCapabilities()?.hasCapability(SpatialCapabilities.SPATIAL_CAPABILITY_3D_CONTENT) == true) {
            wolfModel = session.createGltfResourceAsync(resourcePath).await()
            wolfModel?.let {
                wolfEntity = session.createGltfEntity(model = it).apply {
                    setScale(0.1f)
                    setHidden(true)
                    //setPose(Pose(translation = Vector3(x = -0.2f, y = -0.5f), rotation = Quaternion(y = 0.9f)))
                    //startAnimation(loop = true, animationName = Animations.Walk.name)
                }
            }
        }
    }

    fun move(pose: Pose) {
        wolfEntity?.let { wolf ->
            wolf.apply {
                setHidden(false)
                setPose(pose)
            }
        }
    }

    suspend fun animateWolf(session: Session?, animation: Animations) {
        if(wolfEntity == null) {
            init(session)
        }

        wolfEntity?.let { wolf ->
            wolf.apply {
                setHidden(false)
                startAnimation(loop = true, animationName = animation.name)
            }
        }
    }

    fun stopAnimation() = wolfEntity?.stopAnimation()

    fun freeResources() {
        wolfEntity?.dispose()
        wolfEntity = null
        wolfModel = null
    }

    sealed class Animations(val name: String) {
        data object Attack: Animations("Attack")
        data object Death: Animations("Death")
        data object Eating: Animations("Eating")
        data object Gallop: Animations("Gallop")
        data object GallopJump: Animations("Gallop_Jump")
        data object Walk: Animations("Walk")
    }
}
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -s")
set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -s")

set(libs_include_opus_DIR src/main/cpp/libopus)
include_directories(
        ${libs_include_opus_DIR}/include
        ${libs_include_opus_DIR}/celt
        ${libs_include_opus_DIR}/silk
        ${libs_include_opus_DIR}/silk/float
        ${libs_include_opus_DIR}/src)

add_library(
        opusutil-lib

        SHARED

        src/main/cpp/util.c
        src/main/cpp/media_jni.c
        src/main/cpp/jni_utils.c
        src/main/cpp/libopus/src/opus_multistream_decoder.c
        src/main/cpp/libopus/src/opus_multistream_encoder.c
        src/main/cpp/libopus/src/opus_multistream.c
        src/main/cpp/libopus/src/opus_encoder.c
        src/main/cpp/libopus/celt/celt_encoder.c
        src/main/cpp/libopus/celt/bands.c
        src/main/cpp/libopus/celt/entcode.c
        src/main/cpp/libopus/celt/entdec.c
        src/main/cpp/libopus/celt/entenc.c
        src/main/cpp/libopus/celt/mathops.c
        src/main/cpp/libopus/celt/vq.c
        src/main/cpp/libopus/celt/cwrs.c
        src/main/cpp/libopus/celt/celt.c
        src/main/cpp/libopus/celt/mdct.c
        src/main/cpp/libopus/celt/kiss_fft.c
        src/main/cpp/libopus/celt/bands.c
        src/main/cpp/libopus/celt/pitch.c
        src/main/cpp/libopus/celt/celt_lpc.c
        src/main/cpp/libopus/celt/quant_bands.c
        src/main/cpp/libopus/celt/laplace.c
        src/main/cpp/libopus/celt/modes.c
        src/main/cpp/libopus/celt/rate.c
        src/main/cpp/libopus/silk/lin2log.c
        src/main/cpp/libopus/silk/enc_API.c
        src/main/cpp/libopus/silk/resampler.c
        src/main/cpp/libopus/silk/resampler_private_IIR_FIR.c
        src/main/cpp/libopus/silk/resampler_private_up2_HQ.c
        src/main/cpp/libopus/silk/resampler_private_down_FIR.c
        src/main/cpp/libopus/silk/resampler_private_AR2.c
        src/main/cpp/libopus/silk/resampler_rom.c
        src/main/cpp/libopus/silk/float/encode_frame_FLP.c
        src/main/cpp/libopus/silk/gain_quant.c
        src/main/cpp/libopus/silk/log2lin.c
        src/main/cpp/libopus/silk/encode_pulses.c
        src/main/cpp/libopus/silk/code_signs.c
        src/main/cpp/libopus/silk/tables_pulses_per_block.c
        src/main/cpp/libopus/silk/tables_other.c
        src/main/cpp/libopus/silk/shell_coder.c
        src/main/cpp/libopus/silk/encode_indices.c
        src/main/cpp/libopus/silk/tables_LTP.c
        src/main/cpp/libopus/silk/tables_pitch_lag.c
        src/main/cpp/libopus/silk/NLSF_unpack.c
        src/main/cpp/libopus/silk/tables_gain.c
        src/main/cpp/libopus/silk/float/wrappers_FLP.c
        src/main/cpp/libopus/silk/quant_LTP_gains.c
        src/main/cpp/libopus/silk/VQ_WMat_EC.c
        src/main/cpp/libopus/silk/NSQ.c
        src/main/cpp/libopus/silk/LPC_analysis_filter.c
        src/main/cpp/libopus/silk/NSQ_del_dec.c
        src/main/cpp/libopus/silk/process_NLSFs.c
        src/main/cpp/libopus/silk/NLSF2A.c
        src/main/cpp/libopus/silk/bwexpander_32.c
        src/main/cpp/libopus/silk/LPC_inv_pred_gain.c
        src/main/cpp/libopus/silk/table_LSF_cos.c
        src/main/cpp/libopus/silk/NLSF_encode.c
        src/main/cpp/libopus/silk/NLSF_decode.c
        src/main/cpp/libopus/silk/NLSF_stabilize.c
        src/main/cpp/libopus/silk/sort.c
        src/main/cpp/libopus/silk/NLSF_VQ_weights_laroia.c
        src/main/cpp/libopus/silk/NLSF_del_dec_quant.c
        src/main/cpp/libopus/silk/NLSF_VQ.c
        src/main/cpp/libopus/silk/interpolate.c
        src/main/cpp/libopus/silk/float/wrappers_FLP.c
        src/main/cpp/libopus/silk/A2NLSF.c
        src/main/cpp/libopus/silk/float/process_gains_FLP.c
        src/main/cpp/libopus/silk/float/find_pred_coefs_FLP.c
        src/main/cpp/libopus/silk/float/residual_energy_FLP.c
        src/main/cpp/libopus/silk/float/energy_FLP.c
        src/main/cpp/libopus/silk/float/LPC_analysis_filter_FLP.c
        src/main/cpp/libopus/silk/float/find_LPC_FLP.c
        src/main/cpp/libopus/silk/float/burg_modified_FLP.c
        src/main/cpp/libopus/silk/float/inner_product_FLP.c
        src/main/cpp/libopus/silk/float/scale_copy_vector_FLP.c
        src/main/cpp/libopus/silk/float/LTP_analysis_filter_FLP.c
        src/main/cpp/libopus/silk/float/LTP_scale_ctrl_FLP.c
        src/main/cpp/libopus/silk/float/find_LTP_FLP.c
        src/main/cpp/libopus/silk/float/scale_vector_FLP.c
        src/main/cpp/libopus/silk/float/regularize_correlations_FLP.c
        src/main/cpp/libopus/silk/float/corrMatrix_FLP.c
        src/main/cpp/libopus/silk/float/noise_shape_analysis_FLP.c
        src/main/cpp/libopus/silk/float/bwexpander_FLP.c
        src/main/cpp/libopus/silk/float/LPC_inv_pred_gain_FLP.c
        src/main/cpp/libopus/silk/float/autocorrelation_FLP.c
        src/main/cpp/libopus/silk/float/warped_autocorrelation_FLP.c
        src/main/cpp/libopus/silk/float/apply_sine_window_FLP.c
        src/main/cpp/libopus/silk/float/find_pitch_lags_FLP.c
        src/main/cpp/libopus/silk/float/pitch_analysis_core_FLP.c
        src/main/cpp/libopus/silk/pitch_est_tables.c
        src/main/cpp/libopus/silk/float/sort_FLP.c
        src/main/cpp/libopus/silk/resampler_down2.c
        src/main/cpp/libopus/silk/resampler_down2_3.c
        src/main/cpp/libopus/silk/float/k2a_FLP.c
        src/main/cpp/libopus/silk/float/schur_FLP.c
        src/main/cpp/libopus/silk/LP_variable_cutoff.c
        src/main/cpp/libopus/silk/biquad_alt.c
        src/main/cpp/libopus/silk/VAD.c
        src/main/cpp/libopus/silk/sigm_Q15.c
        src/main/cpp/libopus/silk/ana_filt_bank_1.c
        src/main/cpp/libopus/silk/control_SNR.c
        src/main/cpp/libopus/silk/stereo_encode_pred.c
        src/main/cpp/libopus/silk/stereo_LR_to_MS.c
        src/main/cpp/libopus/silk/stereo_quant_pred.c
        src/main/cpp/libopus/silk/stereo_find_predictor.c
        src/main/cpp/libopus/silk/inner_prod_aligned.c
        src/main/cpp/libopus/silk/sum_sqr_shift.c
        src/main/cpp/libopus/silk/HP_variable_cutoff.c
        src/main/cpp/libopus/silk/control_codec.c
        src/main/cpp/libopus/silk/tables_NLSF_CB_NB_MB.c
        src/main/cpp/libopus/silk/tables_NLSF_CB_WB.c
        src/main/cpp/libopus/silk/control_audio_bandwidth.c
        src/main/cpp/libopus/silk/init_encoder.c
        src/main/cpp/libopus/silk/check_control_input.c
        src/main/cpp/libopus/src/analysis.c
        src/main/cpp/libopus/src/repacketizer.c
        src/main/cpp/libopus/src/opus.c
        src/main/cpp/libopus/src/opus_decoder.c
        src/main/cpp/libopus/src/opus_projection_encoder.c
        src/main/cpp/libopus/src/opus_projection_decoder.c
        src/main/cpp/libopus/src/mapping_matrix.c
        src/main/cpp/libopus/src/mapping_matrix.h
        src/main/cpp/libopus/celt/celt_decoder.c
        src/main/cpp/libopus/silk/dec_API.c
        src/main/cpp/libopus/silk/stereo_MS_to_LR.c
        src/main/cpp/libopus/silk/decode_frame.c
        src/main/cpp/libopus/silk/PLC.c
        src/main/cpp/libopus/silk/bwexpander.c
        src/main/cpp/libopus/silk/CNG.c
        src/main/cpp/libopus/silk/decode_core.c
        src/main/cpp/libopus/silk/decode_parameters.c
        src/main/cpp/libopus/silk/decode_pitch.c
        src/main/cpp/libopus/silk/decode_pulses.c
        src/main/cpp/libopus/silk/decode_indices.c
        src/main/cpp/libopus/silk/stereo_decode_pred.c
        src/main/cpp/libopus/silk/decoder_set_fs.c
        src/main/cpp/libopus/silk/init_decoder.c
        src/main/cpp/libopus/src/mlp.c
        src/main/cpp/libopus/src/mlp_data.c
        src/main/cpp/libopus/silk/LPC_fit.c
        )


find_library(
        log-lib
        log)
find_library(android-lib
        android)

target_link_libraries(
        opusutil-lib
        ${log-lib}
        ${android-lib}
        )
add_definitions(-DOUTSIDE_SPEEX -DOPUS_BUILD -DSTDC_HEADERS -DVAR_ARRAYS)
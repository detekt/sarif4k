package io.github.detekt.sarif4k

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Using https://app.quicktype.io/ to generate from schema
 * https://github.com/oasis-tcs/sarif-spec/blob/5280a944e8faa17a60ab15917a5449e27ed5b32c/Schemata/sarif-external-property-file-schema-2.1.0.json
 *
 * Manually added `@Serializable(with = )` for enums. This can be automated
 * once https://github.com/quicktype/quicktype/pull/1680 is merged.
 *
 * Manually modified the version to be immediately after schema based on the recommendation for
 * consumers to sniff the version before parsing the entire file.
 * https://github.com/detekt/detekt/issues/3045#issuecomment-711071231
 */
/**
 * Static Analysis Results Format (SARIF) Version 2.1.0 JSON Schema: a standard format for
 * the output of static analysis tools.
 */
@Serializable
data class SarifSchema210 (
    /**
     * The URI of the JSON schema corresponding to the version.
     */
    @SerialName("\$schema")
    val schema: String? = null,

    /**
     * The SARIF format version of this log file.
     */
    val version: Version,

    /**
     * References to external property files that share data between runs.
     */
    val inlineExternalProperties: List<ExternalProperties>? = null,

    /**
     * Key/value pairs that provide additional information about the log file.
     */
    val properties: PropertyBag? = null,

    /**
     * The set of runs contained in this log file.
     */
    val runs: List<Run>,
)

/**
 * The top-level element of an external property file.
 */
@Serializable
data class ExternalProperties (
    /**
     * Addresses that will be merged with a separate run.
     */
    val addresses: List<Address>? = null,

    /**
     * An array of artifact objects that will be merged with a separate run.
     */
    val artifacts: List<Artifact>? = null,

    /**
     * A conversion object that will be merged with a separate run.
     */
    val conversion: Conversion? = null,

    /**
     * The analysis tool object that will be merged with a separate run.
     */
    val driver: ToolComponent? = null,

    /**
     * Tool extensions that will be merged with a separate run.
     */
    val extensions: List<ToolComponent>? = null,

    /**
     * Key/value pairs that provide additional information that will be merged with a separate
     * run.
     */
    val externalizedProperties: PropertyBag? = null,

    /**
     * An array of graph objects that will be merged with a separate run.
     */
    val graphs: List<Graph>? = null,

    /**
     * A stable, unique identifer for this external properties object, in the form of a GUID.
     */
    val guid: String? = null,

    /**
     * Describes the invocation of the analysis tool that will be merged with a separate run.
     */
    val invocations: List<Invocation>? = null,

    /**
     * An array of logical locations such as namespaces, types or functions that will be merged
     * with a separate run.
     */
    val logicalLocations: List<LogicalLocation>? = null,

    /**
     * Tool policies that will be merged with a separate run.
     */
    val policies: List<ToolComponent>? = null,

    /**
     * Key/value pairs that provide additional information about the external properties.
     */
    val properties: PropertyBag? = null,

    /**
     * An array of result objects that will be merged with a separate run.
     */
    val results: List<Result>? = null,

    /**
     * A stable, unique identifer for the run associated with this external properties object,
     * in the form of a GUID.
     */
    @SerialName("runGuid")
    val runGUID: String? = null,

    /**
     * The URI of the JSON schema corresponding to the version of the external property file
     * format.
     */
    val schema: String? = null,

    /**
     * Tool taxonomies that will be merged with a separate run.
     */
    val taxonomies: List<ToolComponent>? = null,

    /**
     * An array of threadFlowLocation objects that will be merged with a separate run.
     */
    val threadFlowLocations: List<ThreadFlowLocation>? = null,

    /**
     * Tool translations that will be merged with a separate run.
     */
    val translations: List<ToolComponent>? = null,

    /**
     * The SARIF format version of this external properties object.
     */
    val version: Version? = null,

    /**
     * Requests that will be merged with a separate run.
     */
    val webRequests: List<WebRequest>? = null,

    /**
     * Responses that will be merged with a separate run.
     */
    val webResponses: List<WebResponse>? = null
)

/**
 * A physical or virtual address, or a range of addresses, in an 'addressable region'
 * (memory or a binary file).
 *
 * The address of the location.
 */
@Serializable
data class Address (
    /**
     * The address expressed as a byte offset from the start of the addressable region.
     */
    val absoluteAddress: Long? = null,

    /**
     * A human-readable fully qualified name that is associated with the address.
     */
    val fullyQualifiedName: String? = null,

    /**
     * The index within run.addresses of the cached object for this address.
     */
    val index: Long? = null,

    /**
     * An open-ended string that identifies the address kind. 'data', 'function',
     * 'header','instruction', 'module', 'page', 'section', 'segment', 'stack', 'stackFrame',
     * 'table' are well-known values.
     */
    val kind: String? = null,

    /**
     * The number of bytes in this range of addresses.
     */
    val length: Long? = null,

    /**
     * A name that is associated with the address, e.g., '.text'.
     */
    val name: String? = null,

    /**
     * The byte offset of this address from the absolute or relative address of the parent
     * object.
     */
    val offsetFromParent: Long? = null,

    /**
     * The index within run.addresses of the parent object.
     */
    val parentIndex: Long? = null,

    /**
     * Key/value pairs that provide additional information about the address.
     */
    val properties: PropertyBag? = null,

    /**
     * The address expressed as a byte offset from the absolute address of the top-most parent
     * object.
     */
    val relativeAddress: Long? = null
)

/**
 * Key/value pairs that provide additional information about the address.
 *
 * Key/value pairs that provide additional information about the object.
 *
 * Key/value pairs that provide additional information about the artifact content.
 *
 * Key/value pairs that provide additional information about the message.
 *
 * Key/value pairs that provide additional information about the artifact location.
 *
 * Key/value pairs that provide additional information about the artifact.
 *
 * Contains configuration information specific to a report.
 *
 * Key/value pairs that provide additional information about the reporting configuration.
 *
 * Key/value pairs that provide additional information about the reporting descriptor
 * reference.
 *
 * Key/value pairs that provide additional information about the toolComponentReference.
 *
 * Key/value pairs that provide additional information about the configuration override.
 *
 * Key/value pairs that provide additional information about the invocation.
 *
 * Key/value pairs that provide additional information about the exception.
 *
 * Key/value pairs that provide additional information about the region.
 *
 * Key/value pairs that provide additional information about the logical location.
 *
 * Key/value pairs that provide additional information about the physical location.
 *
 * Key/value pairs that provide additional information about the location.
 *
 * Key/value pairs that provide additional information about the location relationship.
 *
 * Key/value pairs that provide additional information about the stack frame.
 *
 * Key/value pairs that provide additional information about the stack.
 *
 * Key/value pairs that provide additional information about the notification.
 *
 * Key/value pairs that provide additional information about the conversion.
 *
 * Key/value pairs that provide additional information about the report.
 *
 * Key/value pairs that provide additional information about the tool component.
 *
 * Key/value pairs that provide additional information about the translation metadata.
 *
 * Key/value pairs that provide additional information about the tool.
 *
 * Key/value pairs that provide additional information that will be merged with a separate
 * run.
 *
 * Key/value pairs that provide additional information about the edge.
 *
 * Key/value pairs that provide additional information about the node.
 *
 * Key/value pairs that provide additional information about the graph.
 *
 * Key/value pairs that provide additional information about the external properties.
 *
 * Key/value pairs that provide additional information about the attachment.
 *
 * Key/value pairs that provide additional information about the rectangle.
 *
 * Key/value pairs that provide additional information about the code flow.
 *
 * Key/value pairs that provide additional information about the threadflow location.
 *
 * Key/value pairs that provide additional information about the request.
 *
 * Key/value pairs that provide additional information about the response.
 *
 * Key/value pairs that provide additional information about the thread flow.
 *
 * Key/value pairs that provide additional information about the change.
 *
 * Key/value pairs that provide additional information about the replacement.
 *
 * Key/value pairs that provide additional information about the fix.
 *
 * Key/value pairs that provide additional information about the edge traversal.
 *
 * Key/value pairs that provide additional information about the graph traversal.
 *
 * Key/value pairs that provide additional information about the result.
 *
 * Key/value pairs that provide additional information about the suppression.
 *
 * Key/value pairs that provide additional information about the log file.
 *
 * Key/value pairs that provide additional information about the run automation details.
 *
 * Key/value pairs that provide additional information about the external property file.
 *
 * Key/value pairs that provide additional information about the external property files.
 *
 * Key/value pairs that provide additional information about the run.
 *
 * Key/value pairs that provide additional information about the special locations.
 *
 * Key/value pairs that provide additional information about the version control details.
 */
@Serializable
data class PropertyBag (
    /**
     * A set of distinct strings that provide additional information.
     */
    val tags: List<String>? = null
)

/**
 * A single artifact. In some cases, this artifact might be nested within another artifact.
 */
@Serializable
data class Artifact (
    /**
     * The contents of the artifact.
     */
    val contents: ArtifactContent? = null,

    /**
     * A short description of the artifact.
     */
    val description: Message? = null,

    /**
     * Specifies the encoding for an artifact object that refers to a text file.
     */
    val encoding: String? = null,

    /**
     * A dictionary, each of whose keys is the name of a hash function and each of whose values
     * is the hashed value of the artifact produced by the specified hash function.
     */
    val hashes: Map<String, String>? = null,

    /**
     * The Coordinated Universal Time (UTC) date and time at which the artifact was most
     * recently modified. See "Date/time properties" in the SARIF spec for the required format.
     */
    @SerialName("lastModifiedTimeUtc")
    val lastModifiedTimeUTC: String? = null,

    /**
     * The length of the artifact in bytes.
     */
    val length: Long? = null,

    /**
     * The location of the artifact.
     */
    val location: ArtifactLocation? = null,

    /**
     * The MIME type (RFC 2045) of the artifact.
     */
    val mimeType: String? = null,

    /**
     * The offset in bytes of the artifact within its containing artifact.
     */
    val offset: Long? = null,

    /**
     * Identifies the index of the immediate parent of the artifact, if this artifact is nested.
     */
    val parentIndex: Long? = null,

    /**
     * Key/value pairs that provide additional information about the artifact.
     */
    val properties: PropertyBag? = null,

    /**
     * The role or roles played by the artifact in the analysis.
     */
    val roles: List<Role>? = null,

    /**
     * Specifies the source language for any artifact object that refers to a text file that
     * contains source code.
     */
    val sourceLanguage: String? = null
)

/**
 * The contents of the artifact.
 *
 * Represents the contents of an artifact.
 *
 * The portion of the artifact contents within the specified region.
 *
 * The body of the request.
 *
 * The body of the response.
 *
 * The content to insert at the location specified by the 'deletedRegion' property.
 */
@Serializable
data class ArtifactContent (
    /**
     * MIME Base64-encoded content from a binary artifact, or from a text artifact in its
     * original encoding.
     */
    val binary: String? = null,

    /**
     * Key/value pairs that provide additional information about the artifact content.
     */
    val properties: PropertyBag? = null,

    /**
     * An alternate rendered representation of the artifact (e.g., a decompiled representation
     * of a binary region).
     */
    val rendered: MultiformatMessageString? = null,

    /**
     * UTF-8-encoded content from a text artifact.
     */
    val text: String? = null
)

/**
 * An alternate rendered representation of the artifact (e.g., a decompiled representation
 * of a binary region).
 *
 * A message string or message format string rendered in multiple formats.
 *
 * A comprehensive description of the tool component.
 *
 * A description of the report. Should, as far as possible, provide details sufficient to
 * enable resolution of any problem indicated by the result.
 *
 * Provides the primary documentation for the report, useful when there is no online
 * documentation.
 *
 * A concise description of the report. Should be a single sentence that is understandable
 * when visible space is limited to a single line of text.
 *
 * A brief description of the tool component.
 *
 * A comprehensive description of the translation metadata.
 *
 * A brief description of the translation metadata.
 */
@Serializable
data class MultiformatMessageString (
    /**
     * A Markdown message string or format string.
     */
    val markdown: String? = null,

    /**
     * Key/value pairs that provide additional information about the message.
     */
    val properties: PropertyBag? = null,

    /**
     * A plain text message string or format string.
     */
    val text: String
)

/**
 * A short description of the artifact.
 *
 * A short description of the artifact location.
 *
 * A message relevant to the region.
 *
 * A message relevant to the location.
 *
 * A description of the location relationship.
 *
 * A message relevant to this call stack.
 *
 * A message that describes the condition that was encountered.
 *
 * A description of the reporting descriptor relationship.
 *
 * A description of the graph.
 *
 * A short description of the edge.
 *
 * A short description of the node.
 *
 * A message describing the role played by the attachment.
 *
 * A message relevant to the rectangle.
 *
 * A message relevant to the code flow.
 *
 * A message relevant to the thread flow.
 *
 * A message that describes the proposed fix, enabling viewers to present the proposed
 * change to an end user.
 *
 * A description of this graph traversal.
 *
 * A message to display to the user as the edge is traversed.
 *
 * A message that describes the result. The first sentence of the message only will be
 * displayed when visible space is limited.
 *
 * A description of the identity and role played within the engineering system by this
 * object's containing run object.
 *
 * Encapsulates a message intended to be read by the end user.
 */
@Serializable
data class Message (
    /**
     * An array of strings to substitute into the message string.
     */
    val arguments: List<String>? = null,

    /**
     * The identifier for this message.
     */
    val id: String? = null,

    /**
     * A Markdown message string.
     */
    val markdown: String? = null,

    /**
     * Key/value pairs that provide additional information about the message.
     */
    val properties: PropertyBag? = null,

    /**
     * A plain text message string.
     */
    val text: String? = null
)

/**
 * The location of the artifact.
 *
 * Specifies the location of an artifact.
 *
 * An absolute URI specifying the location of the executable that was invoked.
 *
 * A file containing the standard error stream from the process that was invoked.
 *
 * A file containing the standard input stream to the process that was invoked.
 *
 * A file containing the standard output stream from the process that was invoked.
 *
 * A file containing the interleaved standard output and standard error stream from the
 * process that was invoked.
 *
 * The working directory for the invocation.
 *
 * Identifies the artifact that the analysis tool was instructed to scan. This need not be
 * the same as the artifact where the result actually occurred.
 *
 * The location of the attachment.
 *
 * The location of the artifact to change.
 *
 * The location of the external property file.
 *
 * Provides a suggestion to SARIF consumers to display file paths relative to the specified
 * location.
 *
 * The location in the local file system to which the root of the repository was mapped at
 * the time of the analysis.
 */
@Serializable
data class ArtifactLocation (
    /**
     * A short description of the artifact location.
     */
    val description: Message? = null,

    /**
     * The index within the run artifacts array of the artifact object associated with the
     * artifact location.
     */
    val index: Long? = null,

    /**
     * Key/value pairs that provide additional information about the artifact location.
     */
    val properties: PropertyBag? = null,

    /**
     * A string containing a valid relative or absolute URI.
     */
    val uri: String? = null,

    /**
     * A string which indirectly specifies the absolute URI with respect to which a relative URI
     * in the "uri" property is interpreted.
     */
    @SerialName("uriBaseId")
    val uriBaseID: String? = null
)

@Serializable(with = Role.Companion::class)
enum class Role(val value: String) {
    Added("added"),
    AnalysisTarget("analysisTarget"),
    Attachment("attachment"),
    DebugOutputFile("debugOutputFile"),
    Deleted("deleted"),
    Directory("directory"),
    Driver("driver"),
    Extension("extension"),
    MemoryContents("memoryContents"),
    Modified("modified"),
    Policy("policy"),
    ReferencedOnCommandLine("referencedOnCommandLine"),
    Renamed("renamed"),
    ResponseFile("responseFile"),
    ResultFile("resultFile"),
    StandardStream("standardStream"),
    Taxonomy("taxonomy"),
    ToolSpecifiedConfiguration("toolSpecifiedConfiguration"),
    TracedFile("tracedFile"),
    Translation("translation"),
    Uncontrolled("uncontrolled"),
    Unmodified("unmodified"),
    UserSpecifiedConfiguration("userSpecifiedConfiguration");

    companion object : KSerializer<Role> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.Role", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): Role = when (val value = decoder.decodeString()) {
            "added"                      -> Added
            "analysisTarget"             -> AnalysisTarget
            "attachment"                 -> Attachment
            "debugOutputFile"            -> DebugOutputFile
            "deleted"                    -> Deleted
            "directory"                  -> Directory
            "driver"                     -> Driver
            "extension"                  -> Extension
            "memoryContents"             -> MemoryContents
            "modified"                   -> Modified
            "policy"                     -> Policy
            "referencedOnCommandLine"    -> ReferencedOnCommandLine
            "renamed"                    -> Renamed
            "responseFile"               -> ResponseFile
            "resultFile"                 -> ResultFile
            "standardStream"             -> StandardStream
            "taxonomy"                   -> Taxonomy
            "toolSpecifiedConfiguration" -> ToolSpecifiedConfiguration
            "tracedFile"                 -> TracedFile
            "translation"                -> Translation
            "uncontrolled"               -> Uncontrolled
            "unmodified"                 -> Unmodified
            "userSpecifiedConfiguration" -> UserSpecifiedConfiguration
            else                         -> throw IllegalArgumentException("Role could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: Role) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * A conversion object that will be merged with a separate run.
 *
 * Describes how a converter transformed the output of a static analysis tool from the
 * analysis tool's native output format into the SARIF format.
 *
 * A conversion object that describes how a converter transformed an analysis tool's native
 * reporting format into the SARIF format.
 */
@Serializable
data class Conversion (
    /**
     * The locations of the analysis tool's per-run log files.
     */
    val analysisToolLogFiles: List<ArtifactLocation>? = null,

    /**
     * An invocation object that describes the invocation of the converter.
     */
    val invocation: Invocation? = null,

    /**
     * Key/value pairs that provide additional information about the conversion.
     */
    val properties: PropertyBag? = null,

    /**
     * A tool object that describes the converter.
     */
    val tool: Tool
)

/**
 * An invocation object that describes the invocation of the converter.
 *
 * The runtime environment of the analysis tool run.
 */
@Serializable
data class Invocation (
    /**
     * The account under which the invocation occurred.
     */
    val account: String? = null,

    /**
     * An array of strings, containing in order the command line arguments passed to the tool
     * from the operating system.
     */
    val arguments: List<String>? = null,

    /**
     * The command line used to invoke the tool.
     */
    val commandLine: String? = null,

    /**
     * The Coordinated Universal Time (UTC) date and time at which the invocation ended. See
     * "Date/time properties" in the SARIF spec for the required format.
     */
    @SerialName("endTimeUtc")
    val endTimeUTC: String? = null,

    /**
     * The environment variables associated with the analysis tool process, expressed as
     * key/value pairs.
     */
    val environmentVariables: Map<String, String>? = null,

    /**
     * An absolute URI specifying the location of the executable that was invoked.
     */
    val executableLocation: ArtifactLocation? = null,

    /**
     * Specifies whether the tool's execution completed successfully.
     */
    val executionSuccessful: Boolean,

    /**
     * The process exit code.
     */
    val exitCode: Long? = null,

    /**
     * The reason for the process exit.
     */
    val exitCodeDescription: String? = null,

    /**
     * The name of the signal that caused the process to exit.
     */
    val exitSignalName: String? = null,

    /**
     * The numeric value of the signal that caused the process to exit.
     */
    val exitSignalNumber: Long? = null,

    /**
     * The machine on which the invocation occurred.
     */
    val machine: String? = null,

    /**
     * An array of configurationOverride objects that describe notifications related runtime
     * overrides.
     */
    val notificationConfigurationOverrides: List<ConfigurationOverride>? = null,

    /**
     * The id of the process in which the invocation occurred.
     */
    @SerialName("processId")
    val processID: Long? = null,

    /**
     * The reason given by the operating system that the process failed to start.
     */
    val processStartFailureMessage: String? = null,

    /**
     * Key/value pairs that provide additional information about the invocation.
     */
    val properties: PropertyBag? = null,

    /**
     * The locations of any response files specified on the tool's command line.
     */
    val responseFiles: List<ArtifactLocation>? = null,

    /**
     * An array of configurationOverride objects that describe rules related runtime overrides.
     */
    val ruleConfigurationOverrides: List<ConfigurationOverride>? = null,

    /**
     * The Coordinated Universal Time (UTC) date and time at which the invocation started. See
     * "Date/time properties" in the SARIF spec for the required format.
     */
    @SerialName("startTimeUtc")
    val startTimeUTC: String? = null,

    /**
     * A file containing the standard error stream from the process that was invoked.
     */
    val stderr: ArtifactLocation? = null,

    /**
     * A file containing the standard input stream to the process that was invoked.
     */
    val stdin: ArtifactLocation? = null,

    /**
     * A file containing the standard output stream from the process that was invoked.
     */
    val stdout: ArtifactLocation? = null,

    /**
     * A file containing the interleaved standard output and standard error stream from the
     * process that was invoked.
     */
    val stdoutStderr: ArtifactLocation? = null,

    /**
     * A list of conditions detected by the tool that are relevant to the tool's configuration.
     */
    val toolConfigurationNotifications: List<Notification>? = null,

    /**
     * A list of runtime conditions detected by the tool during the analysis.
     */
    val toolExecutionNotifications: List<Notification>? = null,

    /**
     * The working directory for the invocation.
     */
    val workingDirectory: ArtifactLocation? = null
)

/**
 * Information about how a specific rule or notification was reconfigured at runtime.
 */
@Serializable
data class ConfigurationOverride (
    /**
     * Specifies how the rule or notification was configured during the scan.
     */
    val configuration: ReportingConfiguration,

    /**
     * A reference used to locate the descriptor whose configuration was overridden.
     */
    val descriptor: ReportingDescriptorReference,

    /**
     * Key/value pairs that provide additional information about the configuration override.
     */
    val properties: PropertyBag? = null
)

/**
 * Specifies how the rule or notification was configured during the scan.
 *
 * Information about a rule or notification that can be configured at runtime.
 *
 * Default reporting configuration information.
 */
@Serializable
data class ReportingConfiguration (
    /**
     * Specifies whether the report may be produced during the scan.
     */
    val enabled: Boolean? = null,

    /**
     * Specifies the failure level for the report.
     */
    val level: Level? = null,

    /**
     * Contains configuration information specific to a report.
     */
    val parameters: PropertyBag? = null,

    /**
     * Key/value pairs that provide additional information about the reporting configuration.
     */
    val properties: PropertyBag? = null,

    /**
     * Specifies the relative priority of the report. Used for analysis output only.
     */
    val rank: Double? = null
)

/**
 * Specifies the failure level for the report.
 *
 * A value specifying the severity level of the notification.
 *
 * A value specifying the severity level of the result.
 */
@Serializable(with = Level.Companion::class)
enum class Level(val value: String) {
    Error("error"),
    None("none"),
    Note("note"),
    Warning("warning");

    companion object : KSerializer<Level> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.Level", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): Level = when (val value = decoder.decodeString()) {
            "error"   -> Error
            "none"    -> None
            "note"    -> Note
            "warning" -> Warning
            else      -> throw IllegalArgumentException("Level could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: Level) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * A reference used to locate the descriptor whose configuration was overridden.
 *
 * A reference used to locate the rule descriptor associated with this notification.
 *
 * A reference used to locate the descriptor relevant to this notification.
 *
 * A reference to the related reporting descriptor.
 *
 * A reference used to locate the rule descriptor relevant to this result.
 *
 * Information about how to locate a relevant reporting descriptor.
 */
@Serializable
data class ReportingDescriptorReference (
    /**
     * A guid that uniquely identifies the descriptor.
     */
    val guid: String? = null,

    /**
     * The id of the descriptor.
     */
    val id: String? = null,

    /**
     * The index into an array of descriptors in toolComponent.ruleDescriptors,
     * toolComponent.notificationDescriptors, or toolComponent.taxonomyDescriptors, depending on
     * context.
     */
    val index: Long? = null,

    /**
     * Key/value pairs that provide additional information about the reporting descriptor
     * reference.
     */
    val properties: PropertyBag? = null,

    /**
     * A reference used to locate the toolComponent associated with the descriptor.
     */
    val toolComponent: ToolComponentReference? = null
)

/**
 * A reference used to locate the toolComponent associated with the descriptor.
 *
 * Identifies a particular toolComponent object, either the driver or an extension.
 *
 * The component which is strongly associated with this component. For a translation, this
 * refers to the component which has been translated. For an extension, this is the driver
 * that provides the extension's plugin model.
 */
@Serializable
data class ToolComponentReference (
    /**
     * The 'guid' property of the referenced toolComponent.
     */
    val guid: String? = null,

    /**
     * An index into the referenced toolComponent in tool.extensions.
     */
    val index: Long? = null,

    /**
     * The 'name' property of the referenced toolComponent.
     */
    val name: String? = null,

    /**
     * Key/value pairs that provide additional information about the toolComponentReference.
     */
    val properties: PropertyBag? = null
)

/**
 * Describes a condition relevant to the tool itself, as opposed to being relevant to a
 * target being analyzed by the tool.
 */
@Serializable
data class Notification (
    /**
     * A reference used to locate the rule descriptor associated with this notification.
     */
    val associatedRule: ReportingDescriptorReference? = null,

    /**
     * A reference used to locate the descriptor relevant to this notification.
     */
    val descriptor: ReportingDescriptorReference? = null,

    /**
     * The runtime exception, if any, relevant to this notification.
     */
    val exception: Exception? = null,

    /**
     * A value specifying the severity level of the notification.
     */
    val level: Level? = null,

    /**
     * The locations relevant to this notification.
     */
    val locations: List<Location>? = null,

    /**
     * A message that describes the condition that was encountered.
     */
    val message: Message,

    /**
     * Key/value pairs that provide additional information about the notification.
     */
    val properties: PropertyBag? = null,

    /**
     * The thread identifier of the code that generated the notification.
     */
    @SerialName("threadId")
    val threadID: Long? = null,

    /**
     * The Coordinated Universal Time (UTC) date and time at which the analysis tool generated
     * the notification.
     */
    @SerialName("timeUtc")
    val timeUTC: String? = null
)

/**
 * The runtime exception, if any, relevant to this notification.
 *
 * Describes a runtime exception encountered during the execution of an analysis tool.
 */
@Serializable
data class Exception (
    /**
     * An array of exception objects each of which is considered a cause of this exception.
     */
    val innerExceptions: List<Exception>? = null,

    /**
     * A string that identifies the kind of exception, for example, the fully qualified type
     * name of an object that was thrown, or the symbolic name of a signal.
     */
    val kind: String? = null,

    /**
     * A message that describes the exception.
     */
    val message: String? = null,

    /**
     * Key/value pairs that provide additional information about the exception.
     */
    val properties: PropertyBag? = null,

    /**
     * The sequence of function calls leading to the exception.
     */
    val stack: Stack? = null
)

/**
 * The sequence of function calls leading to the exception.
 *
 * A call stack that is relevant to a result.
 *
 * The call stack leading to this location.
 */
@Serializable
data class Stack (
    /**
     * An array of stack frames that represents a sequence of calls, rendered in reverse
     * chronological order, that comprise the call stack.
     */
    val frames: List<StackFrame>,

    /**
     * A message relevant to this call stack.
     */
    val message: Message? = null,

    /**
     * Key/value pairs that provide additional information about the stack.
     */
    val properties: PropertyBag? = null
)

/**
 * A function call within a stack trace.
 */
@Serializable
data class StackFrame (
    /**
     * The location to which this stack frame refers.
     */
    val location: Location? = null,

    /**
     * The name of the module that contains the code of this stack frame.
     */
    val module: String? = null,

    /**
     * The parameters of the call that is executing.
     */
    val parameters: List<String>? = null,

    /**
     * Key/value pairs that provide additional information about the stack frame.
     */
    val properties: PropertyBag? = null,

    /**
     * The thread identifier of the stack frame.
     */
    @SerialName("threadId")
    val threadID: Long? = null
)

/**
 * The location to which this stack frame refers.
 *
 * A location within a programming artifact.
 *
 * A code location associated with the node.
 *
 * The code location.
 *
 * Identifies the location associated with the suppression.
 */
@Serializable
data class Location (
    /**
     * A set of regions relevant to the location.
     */
    val annotations: List<Region>? = null,

    /**
     * Value that distinguishes this location from all other locations within a single result
     * object.
     */
    val id: Long? = null,

    /**
     * The logical locations associated with the result.
     */
    val logicalLocations: List<LogicalLocation>? = null,

    /**
     * A message relevant to the location.
     */
    val message: Message? = null,

    /**
     * Identifies the artifact and region.
     */
    val physicalLocation: PhysicalLocation? = null,

    /**
     * Key/value pairs that provide additional information about the location.
     */
    val properties: PropertyBag? = null,

    /**
     * An array of objects that describe relationships between this location and others.
     */
    val relationships: List<LocationRelationship>? = null
)

/**
 * A region within an artifact where a result was detected.
 *
 * Specifies a portion of the artifact that encloses the region. Allows a viewer to display
 * additional context around the region.
 *
 * Specifies a portion of the artifact.
 *
 * The region of the artifact to delete.
 */
@Serializable
data class Region (
    /**
     * The length of the region in bytes.
     */
    val byteLength: Long? = null,

    /**
     * The zero-based offset from the beginning of the artifact of the first byte in the region.
     */
    val byteOffset: Long? = null,

    /**
     * The length of the region in characters.
     */
    val charLength: Long? = null,

    /**
     * The zero-based offset from the beginning of the artifact of the first character in the
     * region.
     */
    val charOffset: Long? = null,

    /**
     * The column number of the character following the end of the region.
     */
    val endColumn: Long? = null,

    /**
     * The line number of the last character in the region.
     */
    val endLine: Long? = null,

    /**
     * A message relevant to the region.
     */
    val message: Message? = null,

    /**
     * Key/value pairs that provide additional information about the region.
     */
    val properties: PropertyBag? = null,

    /**
     * The portion of the artifact contents within the specified region.
     */
    val snippet: ArtifactContent? = null,

    /**
     * Specifies the source language, if any, of the portion of the artifact specified by the
     * region object.
     */
    val sourceLanguage: String? = null,

    /**
     * The column number of the first character in the region.
     */
    val startColumn: Long? = null,

    /**
     * The line number of the first character in the region.
     */
    val startLine: Long? = null
)

/**
 * A logical location of a construct that produced a result.
 */
@Serializable
data class LogicalLocation (
    /**
     * The machine-readable name for the logical location, such as a mangled function name
     * provided by a C++ compiler that encodes calling convention, return type and other details
     * along with the function name.
     */
    val decoratedName: String? = null,

    /**
     * The human-readable fully qualified name of the logical location.
     */
    val fullyQualifiedName: String? = null,

    /**
     * The index within the logical locations array.
     */
    val index: Long? = null,

    /**
     * The type of construct this logical location component refers to. Should be one of
     * 'function', 'member', 'module', 'namespace', 'parameter', 'resource', 'returnType',
     * 'type', 'variable', 'object', 'array', 'property', 'value', 'element', 'text',
     * 'attribute', 'comment', 'declaration', 'dtd' or 'processingInstruction', if any of those
     * accurately describe the construct.
     */
    val kind: String? = null,

    /**
     * Identifies the construct in which the result occurred. For example, this property might
     * contain the name of a class or a method.
     */
    val name: String? = null,

    /**
     * Identifies the index of the immediate parent of the construct in which the result was
     * detected. For example, this property might point to a logical location that represents
     * the namespace that holds a type.
     */
    val parentIndex: Long? = null,

    /**
     * Key/value pairs that provide additional information about the logical location.
     */
    val properties: PropertyBag? = null
)

/**
 * Identifies the artifact and region.
 *
 * A physical location relevant to a result. Specifies a reference to a programming artifact
 * together with a range of bytes or characters within that artifact.
 */
@Serializable
data class PhysicalLocation (
    /**
     * The address of the location.
     */
    val address: Address? = null,

    /**
     * The location of the artifact.
     */
    val artifactLocation: ArtifactLocation? = null,

    /**
     * Specifies a portion of the artifact that encloses the region. Allows a viewer to display
     * additional context around the region.
     */
    val contextRegion: Region? = null,

    /**
     * Key/value pairs that provide additional information about the physical location.
     */
    val properties: PropertyBag? = null,

    /**
     * Specifies a portion of the artifact.
     */
    val region: Region? = null
)

/**
 * Information about the relation of one location to another.
 */
@Serializable
data class LocationRelationship (
    /**
     * A description of the location relationship.
     */
    val description: Message? = null,

    /**
     * A set of distinct strings that categorize the relationship. Well-known kinds include
     * 'includes', 'isIncludedBy' and 'relevant'.
     */
    val kinds: List<String>? = null,

    /**
     * Key/value pairs that provide additional information about the location relationship.
     */
    val properties: PropertyBag? = null,

    /**
     * A reference to the related location.
     */
    val target: Long
)

/**
 * A tool object that describes the converter.
 *
 * The analysis tool that was run.
 *
 * Information about the tool or tool pipeline that generated the results in this run. A run
 * can only contain results produced by a single tool or tool pipeline. A run can aggregate
 * results from multiple log files, as long as context around the tool run (tool
 * command-line arguments and the like) is identical for all aggregated files.
 */
@Serializable
data class Tool (
    /**
     * The analysis tool that was run.
     */
    val driver: ToolComponent,

    /**
     * Tool extensions that contributed to or reconfigured the analysis tool that was run.
     */
    val extensions: List<ToolComponent>? = null,

    /**
     * Key/value pairs that provide additional information about the tool.
     */
    val properties: PropertyBag? = null
)

/**
 * The analysis tool that was run.
 *
 * A component, such as a plug-in or the driver, of the analysis tool that was run.
 *
 * The analysis tool object that will be merged with a separate run.
 */
@Serializable
data class ToolComponent (
    /**
     * The component which is strongly associated with this component. For a translation, this
     * refers to the component which has been translated. For an extension, this is the driver
     * that provides the extension's plugin model.
     */
    val associatedComponent: ToolComponentReference? = null,

    /**
     * The kinds of data contained in this object.
     */
    val contents: List<Content>? = null,

    /**
     * The binary version of the tool component's primary executable file expressed as four
     * non-negative integers separated by a period (for operating systems that express file
     * versions in this way).
     */
    val dottedQuadFileVersion: String? = null,

    /**
     * The absolute URI from which the tool component can be downloaded.
     */
    @SerialName("downloadUri")
    val downloadURI: String? = null,

    /**
     * A comprehensive description of the tool component.
     */
    val fullDescription: MultiformatMessageString? = null,

    /**
     * The name of the tool component along with its version and any other useful identifying
     * information, such as its locale.
     */
    val fullName: String? = null,

    /**
     * A dictionary, each of whose keys is a resource identifier and each of whose values is a
     * multiformatMessageString object, which holds message strings in plain text and
     * (optionally) Markdown format. The strings can include placeholders, which can be used to
     * construct a message in combination with an arbitrary number of additional string
     * arguments.
     */
    val globalMessageStrings: Map<String, MultiformatMessageString>? = null,

    /**
     * A unique identifer for the tool component in the form of a GUID.
     */
    val guid: String? = null,

    /**
     * The absolute URI at which information about this version of the tool component can be
     * found.
     */
    @SerialName("informationUri")
    val informationURI: String? = null,

    /**
     * Specifies whether this object contains a complete definition of the localizable and/or
     * non-localizable data for this component, as opposed to including only data that is
     * relevant to the results persisted to this log file.
     */
    val isComprehensive: Boolean? = null,

    /**
     * The language of the messages emitted into the log file during this run (expressed as an
     * ISO 639-1 two-letter lowercase language code) and an optional region (expressed as an ISO
     * 3166-1 two-letter uppercase subculture code associated with a country or region). The
     * casing is recommended but not required (in order for this data to conform to RFC5646).
     */
    val language: String? = null,

    /**
     * The semantic version of the localized strings defined in this component; maintained by
     * components that provide translations.
     */
    val localizedDataSemanticVersion: String? = null,

    /**
     * An array of the artifactLocation objects associated with the tool component.
     */
    val locations: List<ArtifactLocation>? = null,

    /**
     * The minimum value of localizedDataSemanticVersion required in translations consumed by
     * this component; used by components that consume translations.
     */
    val minimumRequiredLocalizedDataSemanticVersion: String? = null,

    /**
     * The name of the tool component.
     */
    val name: String,

    /**
     * An array of reportingDescriptor objects relevant to the notifications related to the
     * configuration and runtime execution of the tool component.
     */
    val notifications: List<ReportingDescriptor>? = null,

    /**
     * The organization or company that produced the tool component.
     */
    val organization: String? = null,

    /**
     * A product suite to which the tool component belongs.
     */
    val product: String? = null,

    /**
     * A localizable string containing the name of the suite of products to which the tool
     * component belongs.
     */
    val productSuite: String? = null,

    /**
     * Key/value pairs that provide additional information about the tool component.
     */
    val properties: PropertyBag? = null,

    /**
     * A string specifying the UTC date (and optionally, the time) of the component's release.
     */
    @SerialName("releaseDateUtc")
    val releaseDateUTC: String? = null,

    /**
     * An array of reportingDescriptor objects relevant to the analysis performed by the tool
     * component.
     */
    val rules: List<ReportingDescriptor>? = null,

    /**
     * The tool component version in the format specified by Semantic Versioning 2.0.
     */
    val semanticVersion: String? = null,

    /**
     * A brief description of the tool component.
     */
    val shortDescription: MultiformatMessageString? = null,

    /**
     * An array of toolComponentReference objects to declare the taxonomies supported by the
     * tool component.
     */
    val supportedTaxonomies: List<ToolComponentReference>? = null,

    /**
     * An array of reportingDescriptor objects relevant to the definitions of both standalone
     * and tool-defined taxonomies.
     */
    val taxa: List<ReportingDescriptor>? = null,

    /**
     * Translation metadata, required for a translation, not populated by other component types.
     */
    val translationMetadata: TranslationMetadata? = null,

    /**
     * The tool component version, in whatever format the component natively provides.
     */
    val version: String? = null
)

@Serializable(with = Content.Companion::class)
enum class Content(val value: String) {
    LocalizedData("localizedData"),
    NonLocalizedData("nonLocalizedData");

    companion object : KSerializer<Content> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.Content", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): Content = when (val value = decoder.decodeString()) {
            "localizedData"    -> LocalizedData
            "nonLocalizedData" -> NonLocalizedData
            else               -> throw IllegalArgumentException("Content could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: Content) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * Metadata that describes a specific report produced by the tool, as part of the analysis
 * it provides or its runtime reporting.
 */
@Serializable
data class ReportingDescriptor (
    /**
     * Default reporting configuration information.
     */
    val defaultConfiguration: ReportingConfiguration? = null,

    /**
     * An array of unique identifies in the form of a GUID by which this report was known in
     * some previous version of the analysis tool.
     */
    val deprecatedGuids: List<String>? = null,

    /**
     * An array of stable, opaque identifiers by which this report was known in some previous
     * version of the analysis tool.
     */
    @SerialName("deprecatedIds")
    val deprecatedIDS: List<String>? = null,

    /**
     * An array of readable identifiers by which this report was known in some previous version
     * of the analysis tool.
     */
    val deprecatedNames: List<String>? = null,

    /**
     * A description of the report. Should, as far as possible, provide details sufficient to
     * enable resolution of any problem indicated by the result.
     */
    val fullDescription: MultiformatMessageString? = null,

    /**
     * A unique identifer for the reporting descriptor in the form of a GUID.
     */
    val guid: String? = null,

    /**
     * Provides the primary documentation for the report, useful when there is no online
     * documentation.
     */
    val help: MultiformatMessageString? = null,

    /**
     * A URI where the primary documentation for the report can be found.
     */
    @SerialName("helpUri")
    val helpURI: String? = null,

    /**
     * A stable, opaque identifier for the report.
     */
    val id: String,

    /**
     * A set of name/value pairs with arbitrary names. Each value is a multiformatMessageString
     * object, which holds message strings in plain text and (optionally) Markdown format. The
     * strings can include placeholders, which can be used to construct a message in combination
     * with an arbitrary number of additional string arguments.
     */
    val messageStrings: Map<String, MultiformatMessageString>? = null,

    /**
     * A report identifier that is understandable to an end user.
     */
    val name: String? = null,

    /**
     * Key/value pairs that provide additional information about the report.
     */
    val properties: PropertyBag? = null,

    /**
     * An array of objects that describe relationships between this reporting descriptor and
     * others.
     */
    val relationships: List<ReportingDescriptorRelationship>? = null,

    /**
     * A concise description of the report. Should be a single sentence that is understandable
     * when visible space is limited to a single line of text.
     */
    val shortDescription: MultiformatMessageString? = null
)

/**
 * Information about the relation of one reporting descriptor to another.
 */
@Serializable
data class ReportingDescriptorRelationship (
    /**
     * A description of the reporting descriptor relationship.
     */
    val description: Message? = null,

    /**
     * A set of distinct strings that categorize the relationship. Well-known kinds include
     * 'canPrecede', 'canFollow', 'willPrecede', 'willFollow', 'superset', 'subset', 'equal',
     * 'disjoint', 'relevant', and 'incomparable'.
     */
    val kinds: List<String>? = null,

    /**
     * Key/value pairs that provide additional information about the reporting descriptor
     * reference.
     */
    val properties: PropertyBag? = null,

    /**
     * A reference to the related reporting descriptor.
     */
    val target: ReportingDescriptorReference
)

/**
 * Translation metadata, required for a translation, not populated by other component
 * types.
 *
 * Provides additional metadata related to translation.
 */
@Serializable
data class TranslationMetadata (
    /**
     * The absolute URI from which the translation metadata can be downloaded.
     */
    @SerialName("downloadUri")
    val downloadURI: String? = null,

    /**
     * A comprehensive description of the translation metadata.
     */
    val fullDescription: MultiformatMessageString? = null,

    /**
     * The full name associated with the translation metadata.
     */
    val fullName: String? = null,

    /**
     * The absolute URI from which information related to the translation metadata can be
     * downloaded.
     */
    @SerialName("informationUri")
    val informationURI: String? = null,

    /**
     * The name associated with the translation metadata.
     */
    val name: String,

    /**
     * Key/value pairs that provide additional information about the translation metadata.
     */
    val properties: PropertyBag? = null,

    /**
     * A brief description of the translation metadata.
     */
    val shortDescription: MultiformatMessageString? = null
)

/**
 * A network of nodes and directed edges that describes some aspect of the structure of the
 * code (for example, a call graph).
 */
@Serializable
data class Graph (
    /**
     * A description of the graph.
     */
    val description: Message? = null,

    /**
     * An array of edge objects representing the edges of the graph.
     */
    val edges: List<Edge>? = null,

    /**
     * An array of node objects representing the nodes of the graph.
     */
    val nodes: List<Node>? = null,

    /**
     * Key/value pairs that provide additional information about the graph.
     */
    val properties: PropertyBag? = null
)

/**
 * Represents a directed edge in a graph.
 */
@Serializable
data class Edge (
    /**
     * A string that uniquely identifies the edge within its graph.
     */
    val id: String,

    /**
     * A short description of the edge.
     */
    val label: Message? = null,

    /**
     * Key/value pairs that provide additional information about the edge.
     */
    val properties: PropertyBag? = null,

    /**
     * Identifies the source node (the node at which the edge starts).
     */
    @SerialName("sourceNodeId")
    val sourceNodeID: String,

    /**
     * Identifies the target node (the node at which the edge ends).
     */
    @SerialName("targetNodeId")
    val targetNodeID: String
)

/**
 * Represents a node in a graph.
 */
@Serializable
data class Node (
    /**
     * Array of child nodes.
     */
    val children: List<Node>? = null,

    /**
     * A string that uniquely identifies the node within its graph.
     */
    val id: String,

    /**
     * A short description of the node.
     */
    val label: Message? = null,

    /**
     * A code location associated with the node.
     */
    val location: Location? = null,

    /**
     * Key/value pairs that provide additional information about the node.
     */
    val properties: PropertyBag? = null
)

/**
 * A result produced by an analysis tool.
 */
@Serializable
data class Result (
    /**
     * Identifies the artifact that the analysis tool was instructed to scan. This need not be
     * the same as the artifact where the result actually occurred.
     */
    val analysisTarget: ArtifactLocation? = null,

    /**
     * A set of artifacts relevant to the result.
     */
    val attachments: List<Attachment>? = null,

    /**
     * The state of a result relative to a baseline of a previous run.
     */
    val baselineState: BaselineState? = null,

    /**
     * An array of 'codeFlow' objects relevant to the result.
     */
    val codeFlows: List<CodeFlow>? = null,

    /**
     * A stable, unique identifier for the equivalence class of logically identical results to
     * which this result belongs, in the form of a GUID.
     */
    @SerialName("correlationGuid")
    val correlationGUID: String? = null,

    /**
     * A set of strings each of which individually defines a stable, unique identity for the
     * result.
     */
    val fingerprints: Map<String, String>? = null,

    /**
     * An array of 'fix' objects, each of which represents a proposed fix to the problem
     * indicated by the result.
     */
    val fixes: List<Fix>? = null,

    /**
     * An array of zero or more unique graph objects associated with the result.
     */
    val graphs: List<Graph>? = null,

    /**
     * An array of one or more unique 'graphTraversal' objects.
     */
    val graphTraversals: List<GraphTraversal>? = null,

    /**
     * A stable, unique identifer for the result in the form of a GUID.
     */
    val guid: String? = null,

    /**
     * An absolute URI at which the result can be viewed.
     */
    @SerialName("hostedViewerUri")
    val hostedViewerURI: String? = null,

    /**
     * A value that categorizes results by evaluation state.
     */
    val kind: ResultKind? = null,

    /**
     * A value specifying the severity level of the result.
     */
    val level: Level? = null,

    /**
     * The set of locations where the result was detected. Specify only one location unless the
     * problem indicated by the result can only be corrected by making a change at every
     * specified location.
     */
    val locations: List<Location>? = null,

    /**
     * A message that describes the result. The first sentence of the message only will be
     * displayed when visible space is limited.
     */
    val message: Message,

    /**
     * A positive integer specifying the number of times this logically unique result was
     * observed in this run.
     */
    val occurrenceCount: Long? = null,

    /**
     * A set of strings that contribute to the stable, unique identity of the result.
     */
    val partialFingerprints: Map<String, String>? = null,

    /**
     * Key/value pairs that provide additional information about the result.
     */
    val properties: PropertyBag? = null,

    /**
     * Information about how and when the result was detected.
     */
    val provenance: ResultProvenance? = null,

    /**
     * A number representing the priority or importance of the result.
     */
    val rank: Double? = null,

    /**
     * A set of locations relevant to this result.
     */
    val relatedLocations: List<Location>? = null,

    /**
     * A reference used to locate the rule descriptor relevant to this result.
     */
    val rule: ReportingDescriptorReference? = null,

    /**
     * The stable, unique identifier of the rule, if any, to which this result is relevant.
     */
    @SerialName("ruleId")
    val ruleID: String? = null,

    /**
     * The index within the tool component rules array of the rule object associated with this
     * result.
     */
    val ruleIndex: Long? = null,

    /**
     * An array of 'stack' objects relevant to the result.
     */
    val stacks: List<Stack>? = null,

    /**
     * A set of suppressions relevant to this result.
     */
    val suppressions: List<Suppression>? = null,

    /**
     * An array of references to taxonomy reporting descriptors that are applicable to the
     * result.
     */
    val taxa: List<ReportingDescriptorReference>? = null,

    /**
     * A web request associated with this result.
     */
    val webRequest: WebRequest? = null,

    /**
     * A web response associated with this result.
     */
    val webResponse: WebResponse? = null,

    /**
     * The URIs of the work items associated with this result.
     */
    val workItemUris: List<String>? = null
)

/**
 * An artifact relevant to a result.
 */
@Serializable
data class Attachment (
    /**
     * The location of the attachment.
     */
    val artifactLocation: ArtifactLocation,

    /**
     * A message describing the role played by the attachment.
     */
    val description: Message? = null,

    /**
     * Key/value pairs that provide additional information about the attachment.
     */
    val properties: PropertyBag? = null,

    /**
     * An array of rectangles specifying areas of interest within the image.
     */
    val rectangles: List<Rectangle>? = null,

    /**
     * An array of regions of interest within the attachment.
     */
    val regions: List<Region>? = null
)

/**
 * An area within an image.
 */
@Serializable
data class Rectangle (
    /**
     * The Y coordinate of the bottom edge of the rectangle, measured in the image's natural
     * units.
     */
    val bottom: Double? = null,

    /**
     * The X coordinate of the left edge of the rectangle, measured in the image's natural units.
     */
    val left: Double? = null,

    /**
     * A message relevant to the rectangle.
     */
    val message: Message? = null,

    /**
     * Key/value pairs that provide additional information about the rectangle.
     */
    val properties: PropertyBag? = null,

    /**
     * The X coordinate of the right edge of the rectangle, measured in the image's natural
     * units.
     */
    val right: Double? = null,

    /**
     * The Y coordinate of the top edge of the rectangle, measured in the image's natural units.
     */
    val top: Double? = null
)

/**
 * The state of a result relative to a baseline of a previous run.
 */
@Serializable(with = BaselineState.Companion::class)
enum class BaselineState(val value: String) {
    Absent("absent"),
    New("new"),
    Unchanged("unchanged"),
    Updated("updated");

    companion object : KSerializer<BaselineState> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.BaselineState", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): BaselineState = when (val value = decoder.decodeString()) {
            "absent"    -> Absent
            "new"       -> New
            "unchanged" -> Unchanged
            "updated"   -> Updated
            else        -> throw IllegalArgumentException("BaselineState could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: BaselineState) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * A set of threadFlows which together describe a pattern of code execution relevant to
 * detecting a result.
 */
@Serializable
data class CodeFlow (
    /**
     * A message relevant to the code flow.
     */
    val message: Message? = null,

    /**
     * Key/value pairs that provide additional information about the code flow.
     */
    val properties: PropertyBag? = null,

    /**
     * An array of one or more unique threadFlow objects, each of which describes the progress
     * of a program through a thread of execution.
     */
    val threadFlows: List<ThreadFlow>
)

/**
 * Describes a sequence of code locations that specify a path through a single thread of
 * execution such as an operating system or fiber.
 */
@Serializable
data class ThreadFlow (
    /**
     * An string that uniquely identifies the threadFlow within the codeFlow in which it occurs.
     */
    val id: String? = null,

    /**
     * Values of relevant expressions at the start of the thread flow that remain constant.
     */
    val immutableState: Map<String, MultiformatMessageString>? = null,

    /**
     * Values of relevant expressions at the start of the thread flow that may change during
     * thread flow execution.
     */
    val initialState: Map<String, MultiformatMessageString>? = null,

    /**
     * A temporally ordered array of 'threadFlowLocation' objects, each of which describes a
     * location visited by the tool while producing the result.
     */
    val locations: List<ThreadFlowLocation>,

    /**
     * A message relevant to the thread flow.
     */
    val message: Message? = null,

    /**
     * Key/value pairs that provide additional information about the thread flow.
     */
    val properties: PropertyBag? = null
)

/**
 * A location visited by an analysis tool while simulating or monitoring the execution of a
 * program.
 */
@Serializable
data class ThreadFlowLocation (
    /**
     * An integer representing the temporal order in which execution reached this location.
     */
    val executionOrder: Long? = null,

    /**
     * The Coordinated Universal Time (UTC) date and time at which this location was executed.
     */
    @SerialName("executionTimeUtc")
    val executionTimeUTC: String? = null,

    /**
     * Specifies the importance of this location in understanding the code flow in which it
     * occurs. The order from most to least important is "essential", "important",
     * "unimportant". Default: "important".
     */
    val importance: Importance? = null,

    /**
     * The index within the run threadFlowLocations array.
     */
    val index: Long? = null,

    /**
     * A set of distinct strings that categorize the thread flow location. Well-known kinds
     * include 'acquire', 'release', 'enter', 'exit', 'call', 'return', 'branch', 'implicit',
     * 'false', 'true', 'caution', 'danger', 'unknown', 'unreachable', 'taint', 'function',
     * 'handler', 'lock', 'memory', 'resource', 'scope' and 'value'.
     */
    val kinds: List<String>? = null,

    /**
     * The code location.
     */
    val location: Location? = null,

    /**
     * The name of the module that contains the code that is executing.
     */
    val module: String? = null,

    /**
     * An integer representing a containment hierarchy within the thread flow.
     */
    val nestingLevel: Long? = null,

    /**
     * Key/value pairs that provide additional information about the threadflow location.
     */
    val properties: PropertyBag? = null,

    /**
     * The call stack leading to this location.
     */
    val stack: Stack? = null,

    /**
     * A dictionary, each of whose keys specifies a variable or expression, the associated value
     * of which represents the variable or expression value. For an annotation of kind
     * 'continuation', for example, this dictionary might hold the current assumed values of a
     * set of global variables.
     */
    val state: Map<String, MultiformatMessageString>? = null,

    /**
     * An array of references to rule or taxonomy reporting descriptors that are applicable to
     * the thread flow location.
     */
    val taxa: List<ReportingDescriptorReference>? = null,

    /**
     * A web request associated with this thread flow location.
     */
    val webRequest: WebRequest? = null,

    /**
     * A web response associated with this thread flow location.
     */
    val webResponse: WebResponse? = null
)

/**
 * Specifies the importance of this location in understanding the code flow in which it
 * occurs. The order from most to least important is "essential", "important",
 * "unimportant". Default: "important".
 */
@Serializable(with = Importance.Companion::class)
enum class Importance(val value: String) {
    Essential("essential"),
    Important("important"),
    Unimportant("unimportant");

    companion object : KSerializer<Importance> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.Importance", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): Importance = when (val value = decoder.decodeString()) {
            "essential"   -> Essential
            "important"   -> Important
            "unimportant" -> Unimportant
            else          -> throw IllegalArgumentException("Importance could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: Importance) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * A web request associated with this thread flow location.
 *
 * Describes an HTTP request.
 *
 * A web request associated with this result.
 */
@Serializable
data class WebRequest (
    /**
     * The body of the request.
     */
    val body: ArtifactContent? = null,

    /**
     * The request headers.
     */
    val headers: Map<String, String>? = null,

    /**
     * The index within the run.webRequests array of the request object associated with this
     * result.
     */
    val index: Long? = null,

    /**
     * The HTTP method. Well-known values are 'GET', 'PUT', 'POST', 'DELETE', 'PATCH', 'HEAD',
     * 'OPTIONS', 'TRACE', 'CONNECT'.
     */
    val method: String? = null,

    /**
     * The request parameters.
     */
    val parameters: Map<String, String>? = null,

    /**
     * Key/value pairs that provide additional information about the request.
     */
    val properties: PropertyBag? = null,

    /**
     * The request protocol. Example: 'http'.
     */
    val protocol: String? = null,

    /**
     * The target of the request.
     */
    val target: String? = null,

    /**
     * The request version. Example: '1.1'.
     */
    val version: String? = null
)

/**
 * A web response associated with this thread flow location.
 *
 * Describes the response to an HTTP request.
 *
 * A web response associated with this result.
 */
@Serializable
data class WebResponse (
    /**
     * The body of the response.
     */
    val body: ArtifactContent? = null,

    /**
     * The response headers.
     */
    val headers: Map<String, String>? = null,

    /**
     * The index within the run.webResponses array of the response object associated with this
     * result.
     */
    val index: Long? = null,

    /**
     * Specifies whether a response was received from the server.
     */
    val noResponseReceived: Boolean? = null,

    /**
     * Key/value pairs that provide additional information about the response.
     */
    val properties: PropertyBag? = null,

    /**
     * The response protocol. Example: 'http'.
     */
    val protocol: String? = null,

    /**
     * The response reason. Example: 'Not found'.
     */
    val reasonPhrase: String? = null,

    /**
     * The response status code. Example: 451.
     */
    val statusCode: Long? = null,

    /**
     * The response version. Example: '1.1'.
     */
    val version: String? = null
)

/**
 * A proposed fix for the problem represented by a result object. A fix specifies a set of
 * artifacts to modify. For each artifact, it specifies a set of bytes to remove, and
 * provides a set of new bytes to replace them.
 */
@Serializable
data class Fix (
    /**
     * One or more artifact changes that comprise a fix for a result.
     */
    val artifactChanges: List<ArtifactChange>,

    /**
     * A message that describes the proposed fix, enabling viewers to present the proposed
     * change to an end user.
     */
    val description: Message? = null,

    /**
     * Key/value pairs that provide additional information about the fix.
     */
    val properties: PropertyBag? = null
)

/**
 * A change to a single artifact.
 */
@Serializable
data class ArtifactChange (
    /**
     * The location of the artifact to change.
     */
    val artifactLocation: ArtifactLocation,

    /**
     * Key/value pairs that provide additional information about the change.
     */
    val properties: PropertyBag? = null,

    /**
     * An array of replacement objects, each of which represents the replacement of a single
     * region in a single artifact specified by 'artifactLocation'.
     */
    val replacements: List<Replacement>
)

/**
 * The replacement of a single region of an artifact.
 */
@Serializable
data class Replacement (
    /**
     * The region of the artifact to delete.
     */
    val deletedRegion: Region,

    /**
     * The content to insert at the location specified by the 'deletedRegion' property.
     */
    val insertedContent: ArtifactContent? = null,

    /**
     * Key/value pairs that provide additional information about the replacement.
     */
    val properties: PropertyBag? = null
)

/**
 * Represents a path through a graph.
 */
@Serializable
data class GraphTraversal (
    /**
     * A description of this graph traversal.
     */
    val description: Message? = null,

    /**
     * The sequences of edges traversed by this graph traversal.
     */
    val edgeTraversals: List<EdgeTraversal>? = null,

    /**
     * Values of relevant expressions at the start of the graph traversal that remain constant
     * for the graph traversal.
     */
    val immutableState: Map<String, MultiformatMessageString>? = null,

    /**
     * Values of relevant expressions at the start of the graph traversal that may change during
     * graph traversal.
     */
    val initialState: Map<String, MultiformatMessageString>? = null,

    /**
     * Key/value pairs that provide additional information about the graph traversal.
     */
    val properties: PropertyBag? = null,

    /**
     * The index within the result.graphs to be associated with the result.
     */
    val resultGraphIndex: Long? = null,

    /**
     * The index within the run.graphs to be associated with the result.
     */
    val runGraphIndex: Long? = null
)

/**
 * Represents the traversal of a single edge during a graph traversal.
 */
@Serializable
data class EdgeTraversal (
    /**
     * Identifies the edge being traversed.
     */
    @SerialName("edgeId")
    val edgeID: String,

    /**
     * The values of relevant expressions after the edge has been traversed.
     */
    val finalState: Map<String, MultiformatMessageString>? = null,

    /**
     * A message to display to the user as the edge is traversed.
     */
    val message: Message? = null,

    /**
     * Key/value pairs that provide additional information about the edge traversal.
     */
    val properties: PropertyBag? = null,

    /**
     * The number of edge traversals necessary to return from a nested graph.
     */
    val stepOverEdgeCount: Long? = null
)

/**
 * A value that categorizes results by evaluation state.
 */
@Serializable(with = ResultKind.Companion::class)
enum class ResultKind(val value: String) {
    Fail("fail"),
    Informational("informational"),
    NotApplicable("notApplicable"),
    Open("open"),
    Pass("pass"),
    Review("review");

    companion object : KSerializer<ResultKind> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.ResultKind", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): ResultKind = when (val value = decoder.decodeString()) {
            "fail"          -> Fail
            "informational" -> Informational
            "notApplicable" -> NotApplicable
            "open"          -> Open
            "pass"          -> Pass
            "review"        -> Review
            else            -> throw IllegalArgumentException("ResultKind could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: ResultKind) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * Information about how and when the result was detected.
 *
 * Contains information about how and when a result was detected.
 */
@Serializable
data class ResultProvenance (
    /**
     * An array of physicalLocation objects which specify the portions of an analysis tool's
     * output that a converter transformed into the result.
     */
    val conversionSources: List<PhysicalLocation>? = null,

    /**
     * A GUID-valued string equal to the automationDetails.guid property of the run in which the
     * result was first detected.
     */
    @SerialName("firstDetectionRunGuid")
    val firstDetectionRunGUID: String? = null,

    /**
     * The Coordinated Universal Time (UTC) date and time at which the result was first
     * detected. See "Date/time properties" in the SARIF spec for the required format.
     */
    @SerialName("firstDetectionTimeUtc")
    val firstDetectionTimeUTC: String? = null,

    /**
     * The index within the run.invocations array of the invocation object which describes the
     * tool invocation that detected the result.
     */
    val invocationIndex: Long? = null,

    /**
     * A GUID-valued string equal to the automationDetails.guid property of the run in which the
     * result was most recently detected.
     */
    @SerialName("lastDetectionRunGuid")
    val lastDetectionRunGUID: String? = null,

    /**
     * The Coordinated Universal Time (UTC) date and time at which the result was most recently
     * detected. See "Date/time properties" in the SARIF spec for the required format.
     */
    @SerialName("lastDetectionTimeUtc")
    val lastDetectionTimeUTC: String? = null,

    /**
     * Key/value pairs that provide additional information about the result.
     */
    val properties: PropertyBag? = null
)

/**
 * A suppression that is relevant to a result.
 */
@Serializable
data class Suppression (
    /**
     * A stable, unique identifer for the suprression in the form of a GUID.
     */
    val guid: String? = null,

    /**
     * A string representing the justification for the suppression.
     */
    val justification: String? = null,

    /**
     * A string that indicates where the suppression is persisted.
     */
    val kind: SuppressionKind,

    /**
     * Identifies the location associated with the suppression.
     */
    val location: Location? = null,

    /**
     * Key/value pairs that provide additional information about the suppression.
     */
    val properties: PropertyBag? = null,

    /**
     * A string that indicates the review status of the suppression.
     */
    val status: Status? = null
)

/**
 * A string that indicates where the suppression is persisted.
 */
@Serializable(with = SuppressionKind.Companion::class)
enum class SuppressionKind(val value: String) {
    External("external"),
    InSource("inSource");

    companion object : KSerializer<SuppressionKind> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.SuppressionKind", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): SuppressionKind = when (val value = decoder.decodeString()) {
            "external" -> External
            "inSource" -> InSource
            else       -> throw IllegalArgumentException("SuppressionKind could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: SuppressionKind) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * A string that indicates the review status of the suppression.
 */
@Serializable(with = Status.Companion::class)
enum class Status(val value: String) {
    Accepted("accepted"),
    Rejected("rejected"),
    UnderReview("underReview");

    companion object : KSerializer<Status> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.Status", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): Status = when (val value = decoder.decodeString()) {
            "accepted"    -> Accepted
            "rejected"    -> Rejected
            "underReview" -> UnderReview
            else          -> throw IllegalArgumentException("Status could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: Status) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * The SARIF format version of this external properties object.
 *
 * The SARIF format version of this log file.
 */
@Serializable(with = Version.Companion::class)
enum class Version(val value: String) {
    The210("2.1.0");

    companion object : KSerializer<Version> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.Version", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): Version = when (val value = decoder.decodeString()) {
            "2.1.0" -> The210
            else    -> throw IllegalArgumentException("Version could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: Version) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * Describes a single run of an analysis tool, and contains the reported output of that run.
 */
@Serializable
data class Run (
    /**
     * Addresses associated with this run instance, if any.
     */
    val addresses: List<Address>? = null,

    /**
     * An array of artifact objects relevant to the run.
     */
    val artifacts: List<Artifact>? = null,

    /**
     * Automation details that describe this run.
     */
    val automationDetails: RunAutomationDetails? = null,

    /**
     * The 'guid' property of a previous SARIF 'run' that comprises the baseline that was used
     * to compute result 'baselineState' properties for the run.
     */
    @SerialName("baselineGuid")
    val baselineGUID: String? = null,

    /**
     * Specifies the unit in which the tool measures columns.
     */
    val columnKind: ColumnKind? = null,

    /**
     * A conversion object that describes how a converter transformed an analysis tool's native
     * reporting format into the SARIF format.
     */
    val conversion: Conversion? = null,

    /**
     * Specifies the default encoding for any artifact object that refers to a text file.
     */
    val defaultEncoding: String? = null,

    /**
     * Specifies the default source language for any artifact object that refers to a text file
     * that contains source code.
     */
    val defaultSourceLanguage: String? = null,

    /**
     * References to external property files that should be inlined with the content of a root
     * log file.
     */
    val externalPropertyFileReferences: ExternalPropertyFileReferences? = null,

    /**
     * An array of zero or more unique graph objects associated with the run.
     */
    val graphs: List<Graph>? = null,

    /**
     * Describes the invocation of the analysis tool.
     */
    val invocations: List<Invocation>? = null,

    /**
     * The language of the messages emitted into the log file during this run (expressed as an
     * ISO 639-1 two-letter lowercase culture code) and an optional region (expressed as an ISO
     * 3166-1 two-letter uppercase subculture code associated with a country or region). The
     * casing is recommended but not required (in order for this data to conform to RFC5646).
     */
    val language: String? = null,

    /**
     * An array of logical locations such as namespaces, types or functions.
     */
    val logicalLocations: List<LogicalLocation>? = null,

    /**
     * An ordered list of character sequences that were treated as line breaks when computing
     * region information for the run.
     */
    val newlineSequences: List<String>? = null,

    /**
     * The artifact location specified by each uriBaseId symbol on the machine where the tool
     * originally ran.
     */
    @SerialName("originalUriBaseIds")
    val originalURIBaseIDS: Map<String, ArtifactLocation>? = null,

    /**
     * Contains configurations that may potentially override both
     * reportingDescriptor.defaultConfiguration (the tool's default severities) and
     * invocation.configurationOverrides (severities established at run-time from the command
     * line).
     */
    val policies: List<ToolComponent>? = null,

    /**
     * Key/value pairs that provide additional information about the run.
     */
    val properties: PropertyBag? = null,

    /**
     * An array of strings used to replace sensitive information in a redaction-aware property.
     */
    val redactionTokens: List<String>? = null,

    /**
     * The set of results contained in an SARIF log. The results array can be omitted when a run
     * is solely exporting rules metadata. It must be present (but may be empty) if a log file
     * represents an actual scan.
     */
    val results: List<Result>? = null,

    /**
     * Automation details that describe the aggregate of runs to which this run belongs.
     */
    val runAggregates: List<RunAutomationDetails>? = null,

    /**
     * A specialLocations object that defines locations of special significance to SARIF
     * consumers.
     */
    val specialLocations: SpecialLocations? = null,

    /**
     * An array of toolComponent objects relevant to a taxonomy in which results are categorized.
     */
    val taxonomies: List<ToolComponent>? = null,

    /**
     * An array of threadFlowLocation objects cached at run level.
     */
    val threadFlowLocations: List<ThreadFlowLocation>? = null,

    /**
     * Information about the tool or tool pipeline that generated the results in this run. A run
     * can only contain results produced by a single tool or tool pipeline. A run can aggregate
     * results from multiple log files, as long as context around the tool run (tool
     * command-line arguments and the like) is identical for all aggregated files.
     */
    val tool: Tool,

    /**
     * The set of available translations of the localized data provided by the tool.
     */
    val translations: List<ToolComponent>? = null,

    /**
     * Specifies the revision in version control of the artifacts that were scanned.
     */
    val versionControlProvenance: List<VersionControlDetails>? = null,

    /**
     * An array of request objects cached at run level.
     */
    val webRequests: List<WebRequest>? = null,

    /**
     * An array of response objects cached at run level.
     */
    val webResponses: List<WebResponse>? = null
)

/**
 * Automation details that describe this run.
 *
 * Information that describes a run's identity and role within an engineering system process.
 */
@Serializable
data class RunAutomationDetails (
    /**
     * A stable, unique identifier for the equivalence class of runs to which this object's
     * containing run object belongs in the form of a GUID.
     */
    @SerialName("correlationGuid")
    val correlationGUID: String? = null,

    /**
     * A description of the identity and role played within the engineering system by this
     * object's containing run object.
     */
    val description: Message? = null,

    /**
     * A stable, unique identifer for this object's containing run object in the form of a GUID.
     */
    val guid: String? = null,

    /**
     * A hierarchical string that uniquely identifies this object's containing run object.
     */
    val id: String? = null,

    /**
     * Key/value pairs that provide additional information about the run automation details.
     */
    val properties: PropertyBag? = null
)

/**
 * Specifies the unit in which the tool measures columns.
 */
@Serializable(with = ColumnKind.Companion::class)
enum class ColumnKind(val value: String) {
    UnicodeCodePoints("unicodeCodePoints"),
    Utf16CodeUnits("utf16CodeUnits");

    companion object : KSerializer<ColumnKind> {
        override val descriptor: SerialDescriptor get() {
            return PrimitiveSerialDescriptor("quicktype.ColumnKind", PrimitiveKind.STRING)
        }
        override fun deserialize(decoder: Decoder): ColumnKind = when (val value = decoder.decodeString()) {
            "unicodeCodePoints" -> UnicodeCodePoints
            "utf16CodeUnits"    -> Utf16CodeUnits
            else                -> throw IllegalArgumentException("ColumnKind could not parse: $value")
        }
        override fun serialize(encoder: Encoder, value: ColumnKind) {
            return encoder.encodeString(value.value)
        }
    }
}

/**
 * References to external property files that should be inlined with the content of a root
 * log file.
 */
@Serializable
data class ExternalPropertyFileReferences (
    /**
     * An array of external property files containing run.addresses arrays to be merged with the
     * root log file.
     */
    val addresses: List<ExternalPropertyFileReference>? = null,

    /**
     * An array of external property files containing run.artifacts arrays to be merged with the
     * root log file.
     */
    val artifacts: List<ExternalPropertyFileReference>? = null,

    /**
     * An external property file containing a run.conversion object to be merged with the root
     * log file.
     */
    val conversion: ExternalPropertyFileReference? = null,

    /**
     * An external property file containing a run.driver object to be merged with the root log
     * file.
     */
    val driver: ExternalPropertyFileReference? = null,

    /**
     * An array of external property files containing run.extensions arrays to be merged with
     * the root log file.
     */
    val extensions: List<ExternalPropertyFileReference>? = null,

    /**
     * An external property file containing a run.properties object to be merged with the root
     * log file.
     */
    val externalizedProperties: ExternalPropertyFileReference? = null,

    /**
     * An array of external property files containing a run.graphs object to be merged with the
     * root log file.
     */
    val graphs: List<ExternalPropertyFileReference>? = null,

    /**
     * An array of external property files containing run.invocations arrays to be merged with
     * the root log file.
     */
    val invocations: List<ExternalPropertyFileReference>? = null,

    /**
     * An array of external property files containing run.logicalLocations arrays to be merged
     * with the root log file.
     */
    val logicalLocations: List<ExternalPropertyFileReference>? = null,

    /**
     * An array of external property files containing run.policies arrays to be merged with the
     * root log file.
     */
    val policies: List<ExternalPropertyFileReference>? = null,

    /**
     * Key/value pairs that provide additional information about the external property files.
     */
    val properties: PropertyBag? = null,

    /**
     * An array of external property files containing run.results arrays to be merged with the
     * root log file.
     */
    val results: List<ExternalPropertyFileReference>? = null,

    /**
     * An array of external property files containing run.taxonomies arrays to be merged with
     * the root log file.
     */
    val taxonomies: List<ExternalPropertyFileReference>? = null,

    /**
     * An array of external property files containing run.threadFlowLocations arrays to be
     * merged with the root log file.
     */
    val threadFlowLocations: List<ExternalPropertyFileReference>? = null,

    /**
     * An array of external property files containing run.translations arrays to be merged with
     * the root log file.
     */
    val translations: List<ExternalPropertyFileReference>? = null,

    /**
     * An array of external property files containing run.requests arrays to be merged with the
     * root log file.
     */
    val webRequests: List<ExternalPropertyFileReference>? = null,

    /**
     * An array of external property files containing run.responses arrays to be merged with the
     * root log file.
     */
    val webResponses: List<ExternalPropertyFileReference>? = null
)

/**
 * An external property file containing a run.conversion object to be merged with the root
 * log file.
 *
 * An external property file containing a run.driver object to be merged with the root log
 * file.
 *
 * An external property file containing a run.properties object to be merged with the root
 * log file.
 *
 * Contains information that enables a SARIF consumer to locate the external property file
 * that contains the value of an externalized property associated with the run.
 */
@Serializable
data class ExternalPropertyFileReference (
    /**
     * A stable, unique identifer for the external property file in the form of a GUID.
     */
    val guid: String? = null,

    /**
     * A non-negative integer specifying the number of items contained in the external property
     * file.
     */
    val itemCount: Long? = null,

    /**
     * The location of the external property file.
     */
    val location: ArtifactLocation? = null,

    /**
     * Key/value pairs that provide additional information about the external property file.
     */
    val properties: PropertyBag? = null
)

/**
 * A specialLocations object that defines locations of special significance to SARIF
 * consumers.
 *
 * Defines locations of special significance to SARIF consumers.
 */
@Serializable
data class SpecialLocations (
    /**
     * Provides a suggestion to SARIF consumers to display file paths relative to the specified
     * location.
     */
    val displayBase: ArtifactLocation? = null,

    /**
     * Key/value pairs that provide additional information about the special locations.
     */
    val properties: PropertyBag? = null
)

/**
 * Specifies the information necessary to retrieve a desired revision from a version control
 * system.
 */
@Serializable
data class VersionControlDetails (
    /**
     * A Coordinated Universal Time (UTC) date and time that can be used to synchronize an
     * enlistment to the state of the repository at that time.
     */
    @SerialName("asOfTimeUtc")
    val asOfTimeUTC: String? = null,

    /**
     * The name of a branch containing the revision.
     */
    val branch: String? = null,

    /**
     * The location in the local file system to which the root of the repository was mapped at
     * the time of the analysis.
     */
    val mappedTo: ArtifactLocation? = null,

    /**
     * Key/value pairs that provide additional information about the version control details.
     */
    val properties: PropertyBag? = null,

    /**
     * The absolute URI of the repository.
     */
    @SerialName("repositoryUri")
    val repositoryURI: String,

    /**
     * A string that uniquely and permanently identifies the revision within the repository.
     */
    @SerialName("revisionId")
    val revisionID: String? = null,

    /**
     * A tag that has been applied to the revision.
     */
    val revisionTag: String? = null
)
